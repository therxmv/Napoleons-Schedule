package com.therxmv.napoleon.ui.profile.component

sealed interface ProfileUiEvent {
    data object LoadData : ProfileUiEvent
    data object EditProfile : ProfileUiEvent
}