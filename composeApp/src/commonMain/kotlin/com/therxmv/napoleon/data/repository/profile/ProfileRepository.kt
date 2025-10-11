package com.therxmv.napoleon.data.repository.profile

import com.therxmv.napoleon.data.repository.model.ProfileModel

interface ProfileRepository {

    val isLoggedIn: Boolean

    fun getNotNullProfileSync(): ProfileModel

    suspend fun getProfile(): ProfileModel?

    suspend fun setProfile(
        year: String,
        facultyPath: String,
        facultyName: String,
        specialtyName: String,
    )
}