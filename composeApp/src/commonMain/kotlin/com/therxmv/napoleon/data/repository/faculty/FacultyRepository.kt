package com.therxmv.napoleon.data.repository.faculty

import com.therxmv.napoleon.data.repository.faculty.model.FacultiesModel
import com.therxmv.napoleon.data.repository.faculty.model.SpecialtyModel
import com.therxmv.napoleon.data.repository.faculty.model.YearsModel
import com.therxmv.napoleon.data.source.remote.result.Result

interface FacultyRepository {

    suspend fun getFaculties(): Result<FacultiesModel>
    suspend fun getYears(facultyPath: String): Result<YearsModel>
    fun getSpecialties(year: String): Result<List<SpecialtyModel>>
}