package com.therxmv.leonui.state

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.therxmv.leonui.button.LeonIconButton
import com.therxmv.leonui.button.LeonIconButtonStyle
import com.therxmv.leonui.text.LeonText
import com.therxmv.leonui.text.LeonTextSize
import com.therxmv.leonui.theme.LeonComponentPreview
import com.therxmv.leonui.theme.LeonTheme
import compose.icons.FeatherIcons
import compose.icons.feathericons.AlertTriangle
import org.jetbrains.compose.resources.stringResource

@Composable
fun LeonError(data: LeonState.Error) {
    LeonError(stringResource(data.messageRes), data.onRetry)
}

@Composable
fun LeonError(
    message: String,
    onRetry: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            modifier = Modifier.size(50.dp),
            imageVector = FeatherIcons.AlertTriangle,
            tint = LeonTheme.colors.error,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(LeonTheme.paddings.vertical.base))

        LeonText(
            text = message,
            size = LeonTextSize.Title1,
            textAlign = TextAlign.Center,
        )

        if (onRetry != null) {
            Spacer(modifier = Modifier.height(12.dp))

            LeonIconButton(
                modifier = Modifier.size(75.dp),
                icon = Icons.Default.Refresh,
                onClick = onRetry,
                style = LeonIconButtonStyle.Filled(),
            )
        }
    }
}

@Preview
@Composable
private fun LeonErrorPreview() {
    LeonComponentPreview {
        LeonError(
            message = "This is an example of error message.",
            onRetry = {},
        )
    }
}