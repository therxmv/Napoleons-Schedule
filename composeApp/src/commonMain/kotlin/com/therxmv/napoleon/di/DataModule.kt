package com.therxmv.napoleon.di

import com.therxmv.napoleon.data.repository.converter.ScheduleConverter
import com.therxmv.napoleon.data.repository.faculty.FacultyRepository
import com.therxmv.napoleon.data.repository.faculty.FacultyRepositoryImpl
import com.therxmv.napoleon.data.repository.info.InfoRepository
import com.therxmv.napoleon.data.repository.info.InfoRepositoryImpl
import com.therxmv.napoleon.data.repository.profile.ProfileRepository
import com.therxmv.napoleon.data.repository.profile.ProfileRepositoryImpl
import com.therxmv.napoleon.data.repository.specialty.SpecialtyRepository
import com.therxmv.napoleon.data.repository.specialty.SpecialtyRepositoryImpl
import com.therxmv.napoleon.data.repository.timetable.TimetableRepository
import com.therxmv.napoleon.data.repository.timetable.TimetableRepositoryImpl
import com.therxmv.napoleon.data.source.remote.napoleon.NapoleonApi
import com.therxmv.napoleon.data.source.remote.napoleon.NapoleonService
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

internal expect val dataStoreModule: Module

internal expect val analyticsModule: Module

val dataModule = module {
    // Local source
    includes(dataStoreModule, analyticsModule)

    // Repository
    single { ScheduleConverter(get(named(KoinDispatchers.Default))) }

    singleOf(::ProfileRepositoryImpl) bind ProfileRepository::class

    single<FacultyRepository> { FacultyRepositoryImpl(get(), get(), get(named(KoinDispatchers.Default))) }

    singleOf(::SpecialtyRepositoryImpl) bind SpecialtyRepository::class

    singleOf(::TimetableRepositoryImpl) bind TimetableRepository::class

    singleOf(::InfoRepositoryImpl) bind InfoRepository::class

    // Remote source
     singleOf(::NapoleonService) bind NapoleonApi::class
//    singleOf(::NapoleonMockService) bind NapoleonApi::class

    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = false
                        ignoreUnknownKeys = true
                        isLenient = true
                        encodeDefaults = true
                    }
                )
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 15_000
                connectTimeoutMillis = 15_000
                socketTimeoutMillis = 15_000
            }
        }
    }
}