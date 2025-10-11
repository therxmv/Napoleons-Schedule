package com.therxmv.napoleon.base.ui

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.Clipboard

@OptIn(ExperimentalComposeUiApi::class)
actual suspend fun Clipboard.copyText(text: String) {
    val clipEntry = ClipEntry.withPlainText(text)
    setClipEntry(clipEntry)
}