package com.therxmv.leonui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.window.Dialog
import com.therxmv.leonui.button.LeonButton
import com.therxmv.leonui.button.LeonButtonStyle
import com.therxmv.leonui.text.LeonText
import com.therxmv.leonui.text.LeonTextSize
import com.therxmv.leonui.text.LeonTextWeight
import com.therxmv.leonui.theme.LeonTheme

// TODO add preview
@Composable
fun LeonDialog(
    icon: ImageVector,
    title: String,
    cancelLabel: String,
    onCancel: () -> Unit,
    confirmLabel: String,
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit = onCancel,
    content: @Composable ColumnScope.() -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(LeonTheme.shapes.largeRounded)
                .background(LeonTheme.colors.surface)
                .padding(
                    vertical = LeonTheme.paddings.vertical.baggy,
                    horizontal = LeonTheme.paddings.horizontal.baggy,
                ),
        ) {
            Header(icon, title)
            Spacer(modifier = Modifier.height(LeonTheme.paddings.vertical.baggy))

            content()
            Spacer(modifier = Modifier.height(LeonTheme.paddings.vertical.base))

            Actions(
                modifier = Modifier.align(Alignment.End),
                cancelLabel = cancelLabel,
                onCancel = onCancel,
                confirmLabel = confirmLabel,
                onConfirm = onConfirm,
            )
        }
    }
}

@Composable
private fun Header(
    icon: ImageVector,
    title: String,
) {
    Row(
        modifier = Modifier.height(IntrinsicSize.Max),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f),
            imageVector = icon,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(LeonTheme.paddings.horizontal.skinny))

        LeonText(
            text = title,
            size = LeonTextSize.Title1,
            weight = LeonTextWeight.Bold,
        )
    }
}

@Composable
private fun Actions(
    modifier: Modifier = Modifier,
    cancelLabel: String,
    onCancel: () -> Unit,
    confirmLabel: String,
    onConfirm: () -> Unit,
) {
    Row(
        modifier = modifier,
    ) {
        LeonButton(
            label = cancelLabel,
            style = LeonButtonStyle.Text(),
            onClick = onCancel,
        )
        Spacer(modifier = Modifier.width(LeonTheme.paddings.horizontal.skinny))

        LeonButton(
            label = confirmLabel,
            style = LeonButtonStyle.Text(),
            onClick = onConfirm,
        )
    }
}