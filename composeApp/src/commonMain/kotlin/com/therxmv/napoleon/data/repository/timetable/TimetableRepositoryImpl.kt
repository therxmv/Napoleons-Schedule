package com.therxmv.napoleon.data.repository.timetable

import com.therxmv.napoleon.data.repository.timetable.model.TimetableModel
import com.therxmv.napoleon.data.source.local.datastore.DataStoreSource
import com.therxmv.napoleon.data.source.remote.napoleon.NapoleonApi
import com.therxmv.napoleon.data.source.remote.napoleon.dto.toModel
import com.therxmv.napoleon.data.source.remote.result.Result

class TimetableRepositoryImpl(
    private val napoleonApi: NapoleonApi,
    private val dataStoreSource: DataStoreSource,
) : TimetableRepository {

    private var fetchedTimetable: Result.Success<TimetableModel>? = null

    override suspend fun getTimetable(): Result<TimetableModel> =
        Result.of(
            cachedResult = { fetchedTimetable },
            block = {
                napoleonApi
                    .getTimetable()
                    .also { dataStoreSource.setTimetable(it) }
                    .toModel()
            },
            fallbackBlock = {
                requireNotNull(dataStoreSource.getTimetable()?.toModel())
            },
        ).also {
            if (it is Result.Success) {
                fetchedTimetable = it
            }
        }
}