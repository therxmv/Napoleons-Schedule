package com.therxmv.napoleon.ui.editprofile.component

sealed interface EditProfileUiEvent {

    data object SaveProfile : EditProfileUiEvent
}