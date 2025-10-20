package com.therxmv.napoleon.data.repository.faculty

import com.therxmv.napoleon.data.repository.faculty.model.FacultiesModel
import com.therxmv.napoleon.data.repository.faculty.model.SpecialtiesModel
import com.therxmv.napoleon.data.repository.faculty.model.SpecialtyModel
import com.therxmv.napoleon.data.repository.faculty.model.YearsModel
import com.therxmv.napoleon.data.source.local.datastore.DataStoreSource
import com.therxmv.napoleon.data.source.remote.napoleon.NapoleonApi
import com.therxmv.napoleon.data.source.remote.napoleon.dto.SpecialtiesDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.toModel
import com.therxmv.napoleon.data.source.remote.result.Reason
import com.therxmv.napoleon.data.source.remote.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FacultyRepositoryImpl(
    private val napoleonApi: NapoleonApi,
    private val dataStoreSource: DataStoreSource,
    private val defaultDispatcher: CoroutineDispatcher,
) : FacultyRepository {

    private var currentSpecialties: Result<SpecialtiesModel> = Result.Failure(Reason.Error.Generic)

    override suspend fun getFaculties(): Result<FacultiesModel> =
        Result.of(
            block = {
                napoleonApi
                    .getFaculties()
                    .also { dataStoreSource.setFaculties(it) }
                    .toModel()
            },
            fallbackBlock = {
                requireNotNull(dataStoreSource.getFaculties()?.toModel())
            },
        )

    override suspend fun getYears(facultyPath: String): Result<YearsModel> =
        Result.of(
            block = {
                napoleonApi
                    .getSpecialtiesByFaculty(facultyPath)
                    .also { dataStoreSource.setSpecialties(it) }
            },
            fallbackBlock = {
                requireNotNull(dataStoreSource.getSpecialties())
            },
        ).let {
            when (it) {
                is Result.Success<SpecialtiesDto> -> {
                    currentSpecialties = Result.Success(it.data.toModel())

                    Result.Success(it.data.toYears(), it.reason)
                }

                is Result.Failure -> {
                    currentSpecialties = it

                    it
                }
            }
        }

    override fun getSpecialties(year: String): Result<List<SpecialtyModel>> =
        when (val result = currentSpecialties) {
            is Result.Success<SpecialtiesModel> -> {
                val list = requireNotNull(result.data.specialtiesByYear[year])
                Result.Success(
                    data = list,
                )
            }

            is Result.Failure -> result
        }

    private suspend fun SpecialtiesDto.toYears(): YearsModel =
        withContext(defaultDispatcher) {
            YearsModel(
                years = allYears.mapIndexedNotNull { index, list ->
                    index.asYear().takeIf { list.isNotEmpty() }
                },
            )
        }

    private suspend fun SpecialtiesDto.toModel(): SpecialtiesModel =
        withContext(defaultDispatcher) {
            SpecialtiesModel(
                specialtiesByYear = allYears.mapIndexed { index, list ->
                    index.asYear() to list.map { dto -> SpecialtyModel(dto.specialtyName) }
                }.toMap(),
            )
        }

    private fun Int.asYear(): String = "${this + 1}"
}