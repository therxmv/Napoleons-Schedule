package com.therxmv.napoleon.base.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.unit.dp
import com.therxmv.napoleon.ui.theme.NapoleonTheme
import compose.icons.FeatherIcons
import compose.icons.feathericons.Copy
import kotlinx.coroutines.launch

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

    IconButton(
        onClick = {
            coroutineScope.launch {
                localClipboard.copyText(textToCopy)
            }
            onClick()
        },
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = icon,
            contentDescription = "Copy",
            tint = LocalCopyIconColor.current,
        )
    }
}

@Composable
fun CopyTextButton(
    label: String,
    textToCopy: String,
    onClick: () -> Unit = {},
) {
    val localClipboard = LocalClipboard.current
    val coroutineScope = rememberCoroutineScope()

    TextButton(
        colors = NapoleonTheme.colors.textButton,
        onClick = {
            coroutineScope.launch {
                localClipboard.copyText(textToCopy)
            }
            onClick()
        },
    ) {
        Text(label)
    }
}