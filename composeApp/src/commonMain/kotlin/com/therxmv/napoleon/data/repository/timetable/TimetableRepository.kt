package com.therxmv.napoleon.data.repository.timetable

import com.therxmv.napoleon.data.repository.timetable.model.TimetableModel
import com.therxmv.napoleon.data.source.remote.result.Result

interface TimetableRepository {

    suspend fun getTimetable(): Result<TimetableModel>
}