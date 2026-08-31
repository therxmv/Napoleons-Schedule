package com.therxmv.napoleon.data.repository.specialty

import com.therxmv.napoleon.data.repository.profile.model.ProfileModel
import com.therxmv.napoleon.data.repository.specialty.model.ExamsModel
import com.therxmv.napoleon.data.repository.specialty.model.ScheduleModel
import com.therxmv.napoleon.data.source.remote.result.Result

interface SpecialtyRepository {

    suspend fun getSchedule(profile: ProfileModel): Result<ScheduleModel>
    suspend fun getExams(profile: ProfileModel): Result<ExamsModel>
    suspend fun saveExams(profile: ProfileModel, exams: ExamsModel)
}