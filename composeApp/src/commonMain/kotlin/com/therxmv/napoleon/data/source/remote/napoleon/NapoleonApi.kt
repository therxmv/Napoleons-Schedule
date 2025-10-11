package com.therxmv.napoleon.data.source.remote.napoleon

import com.therxmv.napoleon.data.source.remote.napoleon.dto.ExamsDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.FacultiesDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.RatingDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.ScheduleDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.SpecialtiesDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.TimetableDto

interface NapoleonApi {
    suspend fun getFaculties(): FacultiesDto
    suspend fun getSpecialtiesByFaculty(facultyPath: String): SpecialtiesDto
    suspend fun getTimetable(): TimetableDto

    suspend fun getScheduleBySpecialty(faculty: String, year: String, specialty: String): ScheduleDto
    suspend fun getRatingBySpecialty(faculty: String, year: String, specialty: String): RatingDto
    suspend fun getExamsBySpecialty(faculty: String, year: String, specialty: String): ExamsDto
}