package com.therxmv.napoleon.data.source.remote.mock

import com.therxmv.napoleon.data.source.remote.napoleon.NapoleonApi
import com.therxmv.napoleon.data.source.remote.napoleon.dto.ExamsDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.FacultiesDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.ScheduleDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.SpecialtiesDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.TimetableDto

class NapoleonMockService : NapoleonApi {

    override suspend fun getFaculties(): FacultiesDto =
        MockData.facultiesDto

    override suspend fun getSpecialtiesByFaculty(facultyPath: String): SpecialtiesDto =
        MockData.specialtiesDto

    override suspend fun getScheduleBySpecialty(faculty: String, year: String, specialty: String): ScheduleDto =
        MockData.scheduleDto

    override suspend fun getTimetable(): TimetableDto =
        MockData.timetableDto

    override suspend fun getExamsBySpecialty(faculty: String, year: String, specialty: String): ExamsDto =
        MockData.examsDto
}