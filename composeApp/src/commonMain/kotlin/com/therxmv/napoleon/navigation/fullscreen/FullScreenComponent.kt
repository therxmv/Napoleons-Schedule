package com.therxmv.napoleon.navigation.fullscreen

import com.arkivanov.decompose.ComponentContext
import com.therxmv.napoleon.Res
import com.therxmv.napoleon.data.repository.profile.ProfileRepository
import com.therxmv.napoleon.navigation.destination.child.ChildDestination
import com.therxmv.napoleon.navigation.destination.child.ChildDestination.FullScreen

class FullScreenComponent(
    componentContext: ComponentContext,
    currentDestination: ChildDestination,
    canGoBack: () -> Boolean,
    goBack: () -> Unit,
    private val profileRepository: ProfileRepository,
) : ComponentContext by componentContext {

    val data = Data(
        title = currentDestination.resolveTitle(),
        canGoBack = canGoBack,
        goBack = goBack,
    )

    private fun ChildDestination.resolveTitle(): String =
        when (this) {
            FullScreen.CreateProfile -> Res.string.profile_info_title
            FullScreen.EditProfile -> Res.string.edit_profile_title
            FullScreen.Exams -> getExamsTitle()
            FullScreen.Rating -> Res.string.rating_title
            else -> Res.string.app_name
        }

    private fun getExamsTitle(): String {
        val profile = profileRepository.getNotNullProfileSync()
        val specialty = profile.specialtyName
        val faculty = profile.facultyName

        return Res.string.exams_title + " $specialty, $faculty"
    }

    data class Data(
        val title: String,
        val canGoBack: () -> Boolean,
        val goBack: () -> Unit,
    )
}