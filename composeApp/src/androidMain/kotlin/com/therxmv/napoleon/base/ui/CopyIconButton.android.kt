package com.therxmv.napoleon.base.ui

import android.content.ClipData
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.toClipEntry

actual suspend fun Clipboard.copyText(text: String) {
    val clipData = ClipData.newPlainText(null, text)

    setClipEntry(clipData.toClipEntry())
}