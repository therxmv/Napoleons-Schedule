package com.therxmv.napoleon.ui.editprofile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.therxmv.leonui.state.LeonError
import com.therxmv.leonui.state.LeonLoader
import com.therxmv.leonui.state.LeonState
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
        is LeonState.Ready<EditProfileUiData> -> {
            EditProfileContent(
                modifier = modifier,
                data = uiState.data,
                fallbackReason = uiState.cacheReason,
                onEvent = component::onEvent,
            )
        }

        LeonState.Loading -> LeonLoader()

        is LeonState.Error -> LeonError(uiState)

        LeonState.Idle -> Unit
    }
}