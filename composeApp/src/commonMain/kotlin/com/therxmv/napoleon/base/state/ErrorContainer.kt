package com.therxmv.napoleon.base.state

import androidx.compose.runtime.Composable
import com.therxmv.leonui.state.LeonError
import com.therxmv.leonui.theme.LeonPreview
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LeonStateError(
    data: BaseState.Error,
) {
    LeonError(data.message, data.onRetry)
}

@Preview
@Composable
private fun LeonStateErrorPreview() {
    LeonPreview {
        LeonStateError(
            data = BaseState.Error("Error Message"),
        )
    }
}

@Preview
@Composable
private fun LeonStateErrorWithRetryPreview() {
    LeonPreview {
        LeonStateError(
            data = BaseState.Error("Error Message", {}),
        )
    }
}