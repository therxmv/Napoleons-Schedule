package com.therxmv.napoleon.ui.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.therxmv.napoleon.ui.dashboard.component.DashboardComponent
import com.therxmv.napoleon.ui.dashboard.component.DashboardUiEffect
import com.therxmv.napoleon.ui.dashboard.content.DashboardContent

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    component: DashboardComponent,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val uriHandler = LocalUriHandler.current

    val uiState = component.uiState.collectAsStateWithLifecycle().value

    DashboardContent(
        modifier = modifier,
        data = uiState,
        onEvent = component::onEvent,
    )

    LaunchedEffect(lifecycleOwner, component.uiEffect) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            component.uiEffect.collect {
                when (it) {
                    is DashboardUiEffect.OpenWebUrl -> uriHandler.openUri(it.url)
                }
            }
        }
    }
}