package com.therxmv.napoleon.ui.editprofile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.therxmv.napoleon.base.state.BaseState
import com.therxmv.napoleon.base.state.ErrorContainer
import com.therxmv.napoleon.base.state.LoadingContainer
import com.therxmv.napoleon.ui.editprofile.component.EditProfileComponent
import com.therxmv.napoleon.ui.editprofile.component.EditProfileUiData
import com.therxmv.napoleon.ui.editprofile.content.EditProfileContent

@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    component: EditProfileComponent,
) {
    val uiState = component.uiState.collectAsStateWithLifecycle().value

    when (uiState) {
        is BaseState.Ready<EditProfileUiData> -> {
            EditProfileContent(
                modifier = modifier,
                data = uiState.data,
                fallbackReason = uiState.cacheReason,
                onEvent = component::onEvent,
            )
        }

        BaseState.Loading -> LoadingContainer()

        is BaseState.Error -> ErrorContainer(uiState)

        BaseState.Idle -> Unit
    }
}