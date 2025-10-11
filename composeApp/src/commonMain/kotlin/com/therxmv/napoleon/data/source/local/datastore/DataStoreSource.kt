package com.therxmv.napoleon.data.source.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.therxmv.napoleon.data.repository.model.ProfileModel
import com.therxmv.napoleon.data.source.remote.napoleon.dto.ExamsDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.FacultiesDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.RatingDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.ScheduleDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.SpecialtiesDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.TimetableDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

internal const val dataStoreFileName = "napoleon.preferences_pb"

class DataStoreSource(
    private val dataStore: DataStore<Preferences>,
    private val ioDispatcher: CoroutineDispatcher,
) {

    companion object {
        private const val SCHEDULE = "_schedule"
        private const val RATING = "_rating"
        private const val EXAMS = "_exams"
    }

    private val profileKey = stringPreferencesKey("ProfileKey")
    private val facultiesKey = stringPreferencesKey("FacultiesKey")
    private val specialtiesKey = stringPreferencesKey("SpecialtiesKey")
    private val timetableKey = stringPreferencesKey("TimetableKey")

    suspend fun getProfile(): ProfileModel? =
        profileKey.getObject<ProfileModel>()

    suspend fun setProfile(profile: ProfileModel) {
        profileKey.saveObject(profile)
    }

    suspend fun getFaculties(): FacultiesDto? =
        facultiesKey.getObject<FacultiesDto>()

    suspend fun setFaculties(faculties: FacultiesDto) {
        facultiesKey.saveObject(faculties)
    }

    suspend fun getSpecialties(): SpecialtiesDto? =
        specialtiesKey.getObject<SpecialtiesDto>()

    suspend fun setSpecialties(specialtiesDto: SpecialtiesDto) {
        specialtiesKey.saveObject(specialtiesDto)
    }

    suspend fun getTimetable(): TimetableDto? =
        timetableKey.getObject<TimetableDto>()

    suspend fun setTimetable(timetable: TimetableDto) {
        timetableKey.saveObject(timetable)
    }

    suspend fun getScheduleBySpecialty(specialty: String): ScheduleDto? =
        stringPreferencesKey(specialty + SCHEDULE).getObject<ScheduleDto>()

    suspend fun setScheduleBySpecialty(specialty: String, schedule: ScheduleDto) {
        stringPreferencesKey(specialty + SCHEDULE).saveObject(schedule)
    }

    suspend fun getRatingBySpecialty(specialty: String): RatingDto? =
        stringPreferencesKey(specialty + RATING).getObject<RatingDto>()

    suspend fun setRatingBySpecialty(specialty: String, rating: RatingDto) {
        stringPreferencesKey(specialty + RATING).saveObject(rating)
    }

    suspend fun getExamsBySpecialty(specialty: String): ExamsDto? =
        stringPreferencesKey(specialty + EXAMS).getObject<ExamsDto>()

    suspend fun setExamsBySpecialty(specialty: String, exams: ExamsDto) {
        stringPreferencesKey(specialty + EXAMS).saveObject(exams)
    }

    private suspend inline fun <reified T> Preferences.Key<String>.getObject(): T? =
        withContext(ioDispatcher) {
            getValue()?.toObject<T>()
        }

    private suspend inline fun <reified T> Preferences.Key<String>.saveObject(value: T) {
        withContext(ioDispatcher) {
            saveValue(value.toJson())
        }
    }

    private suspend inline fun <reified T> Preferences.Key<T>.getValue(): T? =
        dataStore.data.map { it[this] }.firstOrNull()

    private suspend fun <T> Preferences.Key<T>.saveValue(value: T) {
        dataStore.edit { prefs ->
            prefs[this] = value
        }
    }

    private inline fun <reified T> T.toJson(): String = Json.encodeToString(this)

    private inline fun <reified T> String.toObject(): T? =
        try {
            Json.decodeFromString<T>(this)
        } catch (_: Exception) {
            null
        }
}