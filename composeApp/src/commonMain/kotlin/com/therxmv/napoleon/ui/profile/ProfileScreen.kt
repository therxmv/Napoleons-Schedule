package com.therxmv.napoleon.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.therxmv.leonui.state.LeonLoader
import com.therxmv.napoleon.base.state.BaseState
import com.therxmv.napoleon.base.state.LeonStateError
import com.therxmv.napoleon.ui.profile.component.ProfileComponent
import com.therxmv.napoleon.ui.profile.component.ProfileUiData
import com.therxmv.napoleon.ui.profile.component.ProfileUiEvent
import com.therxmv.napoleon.ui.profile.content.ProfileContent

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    component: ProfileComponent,
) {
    val uiState = component.uiState.collectAsStateWithLifecycle().value

    when (uiState) {
        is BaseState.Ready<ProfileUiData> -> {
            ProfileContent(
                modifier = modifier,
                data = uiState.data,
                onEvent = component::onEvent,
            )
        }

        BaseState.Loading -> LeonLoader()

        is BaseState.Error -> LeonStateError(uiState)

        BaseState.Idle -> Unit
    }

    LaunchedEffect(Unit) {
        component.onEvent(ProfileUiEvent.LoadData)
    }
}