package com.therxmv.napoleon.data.repository.specialty

import com.therxmv.napoleon.data.repository.model.ExamsModel
import com.therxmv.napoleon.data.repository.model.ProfileModel
import com.therxmv.napoleon.data.repository.model.RatingModel
import com.therxmv.napoleon.data.repository.model.ScheduleModel
import com.therxmv.napoleon.data.source.remote.result.Result

interface SpecialtyRepository {

    suspend fun getSchedule(profile: ProfileModel): Result<ScheduleModel>
    suspend fun getRating(profile: ProfileModel): Result<RatingModel>
    suspend fun getExams(profile: ProfileModel): Result<ExamsModel>
}