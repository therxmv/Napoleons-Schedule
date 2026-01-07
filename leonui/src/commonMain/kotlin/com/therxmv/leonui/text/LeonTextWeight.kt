package com.therxmv.leonui.text

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.font.FontWeight

@Stable
sealed class LeonTextWeight(val value: FontWeight) {

    object Normal : LeonTextWeight(FontWeight.Normal)

    object Bold : LeonTextWeight(FontWeight.Bold)
}