package com.therxmv.leonui.extensions

import androidx.compose.ui.Modifier

inline fun Modifier.applyIf(
    predicate: Boolean,
    modifier: Modifier.() -> Modifier,
): Modifier =
    if (predicate) {
        this.modifier()
    } else {
        this
    }