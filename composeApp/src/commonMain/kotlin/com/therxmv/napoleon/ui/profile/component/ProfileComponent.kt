package com.therxmv.napoleon.ui.profile.component

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.therxmv.napoleon.Res
import com.therxmv.napoleon.base.state.BaseState
import com.therxmv.napoleon.data.repository.profile.ProfileRepository
import com.therxmv.napoleon.navigation.destination.child.ChildDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Stable
class ProfileComponent(
    componentContext: ComponentContext,
    private val profileRepository: ProfileRepository,
    private val navigateTo: (ChildDestination) -> Unit,
) : ComponentContext by componentContext {

    private val _uiState = MutableStateFlow<BaseState<ProfileUiData>>(BaseState.Idle)
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: ProfileUiEvent) {
        when (event) {
            ProfileUiEvent.LoadData -> _uiState.update { BaseState.Ready(generateData()) }

            is ProfileUiEvent.EditProfile -> navigateTo(ChildDestination.FullScreen.EditProfile)
        }
    }

    private fun generateData(): ProfileUiData {
        val profile = profileRepository.getNotNullProfileSync()

        return ProfileUiData(
            infoTitle = Res.string.profile_info_title,
            facultyLabel = Res.string.profile_faculty_label,
            faculty = profile.facultyName,
            specialtyLabel = Res.string.profile_specialty_label,
            specialty = profile.specialtyName,
            editButtonLabel = Res.string.profile_edit_button,
        )
    }
}