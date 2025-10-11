package com.therxmv.napoleon.data.source.remote.napoleon

import com.therxmv.napoleon.data.source.remote.BaseUrlProvider
import com.therxmv.napoleon.data.source.remote.napoleon.dto.ExamsDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.FacultiesDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.RatingDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.ScheduleDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.ShiftDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.SpecialtiesDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.TimetableDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class NapoleonService(
    private val httpClient: HttpClient,
) : NapoleonApi {

    companion object {
        private const val SCHEDULE = "schedule"
        private const val RES = "recours"
        private const val EXAMS = "exams"
        private const val RATING = "rating"

        private const val FACULTIES_PATH = "/$RES/all_faculties.json"
        private const val SPECIALTIES_PATH = "/$SCHEDULE/all_years.json"
        private const val FIRST_SHIFT_PATH = "/$RES/time1.json"
        private const val SECOND_SHIFT_PATH = "/$RES/time2.json"
    }

    private val apiUrl = BaseUrlProvider.getApiUrl()

    override suspend fun getFaculties(): FacultiesDto =
        httpClient
            .get("$apiUrl$FACULTIES_PATH")
            .body<FacultiesDto>()

    override suspend fun getSpecialtiesByFaculty(facultyPath: String): SpecialtiesDto =
        httpClient
            .get("$apiUrl/$facultyPath$SPECIALTIES_PATH")
            .body<SpecialtiesDto>()

    override suspend fun getTimetable(): TimetableDto =
        coroutineScope {
            val first = async {
                httpClient
                    .get("$apiUrl$FIRST_SHIFT_PATH")
                    .body<ShiftDto>()
            }
            val second = async {
                httpClient
                    .get("$apiUrl$SECOND_SHIFT_PATH")
                    .body<ShiftDto>()
            }

            TimetableDto(
                firstShift = first.await(),
                secondShift = second.await(),
            )
        }

    override suspend fun getScheduleBySpecialty(faculty: String, year: String, specialty: String): ScheduleDto =
        httpClient
            .get("$apiUrl/$faculty/$SCHEDULE/$year/$specialty.json")
            .body<ScheduleDto>()

    override suspend fun getRatingBySpecialty(faculty: String, year: String, specialty: String): RatingDto =
        httpClient
            .get("$apiUrl/$faculty/$RATING/$year/$specialty.json")
            .body<RatingDto>()

    override suspend fun getExamsBySpecialty(faculty: String, year: String, specialty: String): ExamsDto =
        httpClient
            .get("$apiUrl/$faculty/$EXAMS/$year/$specialty.json")
            .body<ExamsDto>()
}