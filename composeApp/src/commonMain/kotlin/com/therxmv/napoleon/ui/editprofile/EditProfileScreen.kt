package com.therxmv.napoleon.ui.editprofile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.therxmv.leonui.state.LeonLoader
import com.therxmv.napoleon.base.state.BaseState
import com.therxmv.napoleon.base.state.LeonStateError
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

        BaseState.Loading -> LeonLoader()

        is BaseState.Error -> LeonStateError(uiState)

        BaseState.Idle -> Unit
    }
}