package com.therxmv.napoleon.data.repository.timetable

import com.therxmv.napoleon.data.repository.model.TimetableModel
import com.therxmv.napoleon.data.source.remote.result.Result

interface TimetableRepository {

    suspend fun getTimetable(): Result<TimetableModel>
}