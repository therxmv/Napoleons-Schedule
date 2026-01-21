package com.therxmv.napoleon.ui.rating

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.therxmv.leonui.state.ClearFocusWhenKeyboardIsHidden
import com.therxmv.napoleon.ui.rating.component.RatingComponent
import com.therxmv.napoleon.ui.rating.content.RatingContent

@Composable
fun RatingScreen(
    paddingValues: PaddingValues,
    component: RatingComponent,
) {
    val layoutDirection = LocalLayoutDirection.current

    val uiState by component.uiState.collectAsStateWithLifecycle()

    RatingContent(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.ime)
            .padding(
                top = paddingValues.calculateTopPadding(),
                start = paddingValues.calculateStartPadding(layoutDirection),
                end = paddingValues.calculateEndPadding(layoutDirection),
            ),
        data = uiState,
        onEvent = component::onEvent,
    )

    ClearFocusWhenKeyboardIsHidden()
}