package com.therxmv.napoleon.ui.exam

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.therxmv.napoleon.base.state.BaseState
import com.therxmv.napoleon.base.state.ErrorContainer
import com.therxmv.napoleon.base.state.LoadingContainer
import com.therxmv.napoleon.ui.exam.component.ExamsComponent
import com.therxmv.napoleon.ui.exam.component.ExamsUiData
import com.therxmv.napoleon.ui.exam.content.ExamsContent

@Composable
fun ExamsScreen(
    modifier: Modifier = Modifier,
    component: ExamsComponent,
) {
    val uiState = component.uiState.collectAsStateWithLifecycle().value

    when (uiState) {
        is BaseState.Ready<ExamsUiData> -> {
            ExamsContent(
                modifier = modifier,
                data = uiState.data,
                fallbackReason = uiState.cacheReason,
            )
        }

        BaseState.Loading -> LoadingContainer()

        is BaseState.Error -> ErrorContainer(uiState)

        BaseState.Idle -> Unit
    }
}