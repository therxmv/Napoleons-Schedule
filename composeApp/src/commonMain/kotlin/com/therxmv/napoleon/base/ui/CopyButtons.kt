package com.therxmv.napoleon.base.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.LocalClipboard
import com.therxmv.leonui.button.LeonButton
import com.therxmv.leonui.button.LeonButtonStyle
import com.therxmv.leonui.button.LeonIconButton
import com.therxmv.leonui.theme.LeonPreview
import compose.icons.FeatherIcons
import compose.icons.feathericons.Copy
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

expect suspend fun Clipboard.copyText(text: String)

val LocalCopyIconColor = compositionLocalOf { Color.Black }

@Composable
fun CopyIconButton(
    textToCopy: String,
    icon: ImageVector = FeatherIcons.Copy,
    onClick: () -> Unit = {},
) {
    val localClipboard = LocalClipboard.current
    val coroutineScope = rememberCoroutineScope()

    LeonIconButton(
        icon = icon,
        tint = LocalCopyIconColor.current,
        onClick = {
            coroutineScope.launch {
                localClipboard.copyText(textToCopy)
            }
            onClick()
        },
    )
}

@Composable
fun CopyTextButton(
    label: String,
    textToCopy: String,
    onClick: () -> Unit = {},
) {
    val localClipboard = LocalClipboard.current
    val coroutineScope = rememberCoroutineScope()

    LeonButton(
        style = LeonButtonStyle.Text,
        label = label,
        onClick = {
            coroutineScope.launch {
                localClipboard.copyText(textToCopy)
            }
            onClick()
        },
    )
}

@Preview
@Composable
private fun CopyIconButtonPreview() {
    LeonPreview {
        CopyIconButton(textToCopy = "text")
    }
}

@Preview
@Composable
private fun CopyTextButtonPreview() {
    LeonPreview {
        CopyTextButton(label = "Copy", textToCopy = "text")
    }
}