package com.therxmv.napoleon.ui.schedule

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.therxmv.napoleon.base.state.BaseState
import com.therxmv.napoleon.base.state.ErrorContainer
import com.therxmv.napoleon.base.state.LoadingContainer
import com.therxmv.napoleon.ui.schedule.component.ScheduleComponent
import com.therxmv.napoleon.ui.schedule.component.ScheduleUiData
import com.therxmv.napoleon.ui.schedule.component.ScheduleUiEffect
import com.therxmv.napoleon.ui.schedule.content.ScheduleContent

@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    component: ScheduleComponent,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val uriHandler = LocalUriHandler.current

    val uiState = component.uiState.collectAsStateWithLifecycle().value

    when (uiState) {
        is BaseState.Ready<ScheduleUiData> -> {
            ScheduleContent(
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

    LaunchedEffect(lifecycleOwner, component.uiEffect) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            component.uiEffect.collect {
                when (it) {
                    is ScheduleUiEffect.OpenWebUrl -> uriHandler.openUri(it.url)
                }
            }
        }
    }
}