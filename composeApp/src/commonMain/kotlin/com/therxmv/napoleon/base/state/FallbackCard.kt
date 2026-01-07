package com.therxmv.napoleon.base.state

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.therxmv.leonui.theme.LeonTheme
import compose.icons.FeatherIcons
import compose.icons.feathericons.AlertTriangle

@Composable
fun FallbackCard(
    reason: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(LeonTheme.shapes.allRounded)
            .background(MaterialTheme.colorScheme.errorContainer)
            .padding(LeonTheme.paddings.defaultValues),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = FeatherIcons.AlertTriangle,
            tint = MaterialTheme.colorScheme.error,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(LeonTheme.paddings.horizontal))

        Text(
            text = reason,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onErrorContainer,
        )
    }
}