package com.therxmv.napoleon.navigation.fullscreen

import com.arkivanov.decompose.ComponentContext
import com.therxmv.leonres.getSyncString
import com.therxmv.napoleon.data.repository.profile.ProfileRepository
import com.therxmv.napoleon.navigation.destination.child.ChildDestination
import com.therxmv.napoleon.navigation.destination.child.ChildDestination.FullScreen
import napoleon.leonres.generated.resources.Res
import napoleon.leonres.generated.resources.app_name
import napoleon.leonres.generated.resources.edit_profile_title
import napoleon.leonres.generated.resources.exams_title
import napoleon.leonres.generated.resources.profile_info_title
import napoleon.leonres.generated.resources.rating_title

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
            FullScreen.CreateProfile -> getSyncString(Res.string.profile_info_title)
            FullScreen.EditProfile -> getSyncString(Res.string.edit_profile_title)
            FullScreen.Exams -> getExamsTitle()
            FullScreen.Rating -> getSyncString(Res.string.rating_title)
            else -> getSyncString(Res.string.app_name)
        }

    private fun getExamsTitle(): String {
        val profile = profileRepository.getNotNullProfileSync()
        val specialty = profile.specialtyName
        val faculty = profile.facultyName

        return getSyncString(Res.string.exams_title, specialty, faculty)
    }

    data class Data(
        val title: String,
        val canGoBack: () -> Boolean,
        val goBack: () -> Unit,
    )
}