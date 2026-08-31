package com.therxmv.napoleon.ui.profile.component

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.therxmv.leonui.state.LeonState
import com.therxmv.napoleon.data.repository.profile.ProfileRepository
import com.therxmv.napoleon.navigation.destination.child.ChildDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import napoleon.leonres.generated.resources.Res
import napoleon.leonres.generated.resources.profile_edit_button
import napoleon.leonres.generated.resources.profile_faculty_label
import napoleon.leonres.generated.resources.profile_info_title
import napoleon.leonres.generated.resources.profile_specialty_label

@Stable
class ProfileComponent(
    componentContext: ComponentContext,
    private val profileRepository: ProfileRepository,
    private val navigateTo: (ChildDestination) -> Unit,
) : ComponentContext by componentContext {

    private val _uiState = MutableStateFlow<LeonState<ProfileUiData>>(LeonState.Idle)
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: ProfileUiEvent) {
        when (event) {
            ProfileUiEvent.LoadData -> _uiState.update { LeonState.Ready(generateData()) }

            is ProfileUiEvent.EditProfile -> navigateTo(ChildDestination.FullScreen.EditProfile)
        }
    }

    private fun generateData(): ProfileUiData {
        val profile = profileRepository.getNotNullProfileSync()

        return ProfileUiData(
            infoTitleRes = Res.string.profile_info_title,
            facultyLabelRes = Res.string.profile_faculty_label,
            faculty = profile.facultyName,
            specialtyLabelRes = Res.string.profile_specialty_label,
            specialty = profile.specialtyName,
            editButtonLabelRes = Res.string.profile_edit_button,
        )
    }
}