package com.therxmv.napoleon.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.therxmv.leonui.state.LeonError
import com.therxmv.leonui.state.LeonLoader
import com.therxmv.leonui.state.LeonState
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
        is LeonState.Ready<ProfileUiData> -> {
            ProfileContent(
                modifier = modifier,
                data = uiState.data,
                onEvent = component::onEvent,
            )
        }

        LeonState.Loading -> LeonLoader()

        is LeonState.Error -> LeonError(uiState)

        LeonState.Idle -> Unit
    }

    LaunchedEffect(Unit) {
        component.onEvent(ProfileUiEvent.LoadData)
    }
}