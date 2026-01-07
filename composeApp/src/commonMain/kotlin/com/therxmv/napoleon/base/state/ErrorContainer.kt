package com.therxmv.napoleon.base.state

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.therxmv.leonui.theme.LeonTheme
import compose.icons.FeatherIcons
import compose.icons.feathericons.AlertTriangle

@Composable
fun ErrorContainer(
    data: BaseState.Error,
) {
    ErrorContainer(data.message, data.onRetry)
}

@Composable
fun ErrorContainer(
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
        ErrorIcon()
        Spacer(modifier = Modifier.height(LeonTheme.paddings.vertical))

        Text(
            text = message,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
        )

        if (onRetry != null) {
            Spacer(modifier = Modifier.height(12.dp))

            RetryButton(onClick = onRetry)
        }
    }
}

@Composable
private fun ErrorIcon() {
    Icon(
        modifier = Modifier.size(50.dp),
        imageVector = FeatherIcons.AlertTriangle,
        tint = MaterialTheme.colorScheme.error,
        contentDescription = null,
    )
}

@Composable
private fun RetryButton(
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .size(75.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer),
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            imageVector = Icons.Default.Refresh,
            contentDescription = "retry",
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}