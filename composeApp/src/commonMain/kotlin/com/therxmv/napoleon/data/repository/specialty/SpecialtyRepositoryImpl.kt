package com.therxmv.napoleon.data.repository.specialty

import com.therxmv.napoleon.base.date.getNowMillis
import com.therxmv.napoleon.data.repository.converter.ScheduleConverter
import com.therxmv.napoleon.data.repository.profile.model.ProfileModel
import com.therxmv.napoleon.data.repository.specialty.model.ExamsModel
import com.therxmv.napoleon.data.repository.specialty.model.ScheduleModel
import com.therxmv.napoleon.data.source.local.datastore.DataStoreSource
import com.therxmv.napoleon.data.source.remote.mock.MockData
import com.therxmv.napoleon.data.source.remote.napoleon.NapoleonApi
import com.therxmv.napoleon.data.source.remote.napoleon.dto.ScheduleDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.toModel
import com.therxmv.napoleon.data.source.remote.result.Result
import kotlin.time.ExperimentalTime

class SpecialtyRepositoryImpl(
    private val napoleonApi: NapoleonApi,
    private val dataStoreSource: DataStoreSource,
    private val scheduleConverter: ScheduleConverter,
) : SpecialtyRepository {

    companion object {
        private const val SCHEDULE_TTL = 1 * 60000L // 1 min
        private const val DEFAULT_TTL = 5 * 60000L // 5 min
    }

    private val cachedSchedule: MutableMap<String, CacheData<ScheduleModel>> = mutableMapOf()
    private val cachedExams: MutableMap<String, CacheData<ExamsModel>> = mutableMapOf()

    override suspend fun getSchedule(profile: ProfileModel): Result<ScheduleModel> {
        val cachedResult = cachedSchedule[profile.specialtyName]?.let { cache ->
            cache.result.takeIf { cache.timestamp + SCHEDULE_TTL < getNowMillis() }
        }

        return cachedResult ?: Result.of(
            block = {
                napoleonApi
                    .getScheduleBySpecialty(
                        faculty = profile.facultyPath,
                        year = profile.year,
                        specialty = profile.specialtyName,
                    )
                    .also { dataStoreSource.setScheduleBySpecialty(profile.specialtyName, it) }
                    .toModel()
            },
            fallbackBlock = {
                requireNotNull(dataStoreSource.getScheduleBySpecialty(profile.specialtyName)?.toModel())
            },
        ).also { result ->
            if (result is Result.Success) {
                cachedSchedule[profile.specialtyName] = CacheData(result)
            }
        }
    }

    override suspend fun getExams(profile: ProfileModel): Result<ExamsModel> {
        val cachedResult = cachedExams[profile.specialtyName]?.let { cache ->
            cache.result.takeIf { cache.timestamp + DEFAULT_TTL < getNowMillis() }
        }

        return cachedResult ?: Result.of(
            block = {
                // TODO uncomment
                MockData.examsDto.toModel()
//                napoleonApi
//                    .getExamsBySpecialty(
//                        faculty = profile.facultyPath,
//                        year = profile.year,
//                        specialty = profile.specialtyName,
//                    )
//                    .also { dataStoreSource.setExamsBySpecialty(profile.specialtyName, it) }
//                    .toModel()
            },
            fallbackBlock = {
                requireNotNull(dataStoreSource.getExamsBySpecialty(profile.specialtyName)?.toModel())
            },
        ).also { result ->
            if (result is Result.Success) {
                cachedExams[profile.specialtyName] = CacheData(result)
            }
        }
    }

    private suspend fun ScheduleDto.toModel(): ScheduleModel =
        scheduleConverter.dtoToModel(this)

    @OptIn(ExperimentalTime::class)
    private data class CacheData<T>(
        val result: Result<T>,
        val timestamp: Long = getNowMillis(),
    )
}