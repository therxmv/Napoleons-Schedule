package com.therxmv.napoleon.data.repository.profile

import com.therxmv.napoleon.Res
import com.therxmv.napoleon.data.repository.profile.model.ProfileModel
import com.therxmv.napoleon.data.source.local.datastore.DataStoreSource
import kotlinx.coroutines.runBlocking

class ProfileRepositoryImpl(
    private val dataStoreSource: DataStoreSource,
) : ProfileRepository {

    override val isLoggedIn: Boolean
        get() = runBlocking {
            dataStoreSource.getProfile() != null
        }

    // Designed to be used in places where it definitely exists
    override fun getNotNullProfileSync(): ProfileModel =
        runBlocking { requireNotNull(dataStoreSource.getProfile()) }

    override suspend fun getProfile(): ProfileModel? =
        dataStoreSource.getProfile()

    override suspend fun setProfile(
        year: String,
        facultyPath: String,
        facultyName: String,
        specialtyName: String,
    ) {
        val profile = ProfileModel(
            name = Res.string.profile_default_name,
            year = year,
            facultyPath = facultyPath,
            facultyName = facultyName,
            specialtyName = specialtyName,
        )
        dataStoreSource.setProfile(profile)
    }
}