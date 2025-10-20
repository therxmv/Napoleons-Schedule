package com.therxmv.napoleon.ui.rating

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.therxmv.napoleon.base.state.ClearFocusWhenKeyboardIsHidden
import com.therxmv.napoleon.ui.rating.component.RatingComponent
import com.therxmv.napoleon.ui.rating.content.RatingScreenContent

@Composable
fun RatingScreen(
    paddingValues: PaddingValues,
    component: RatingComponent,
) {
    val layoutDirection = LocalLayoutDirection.current

    val uiState by component.uiState.collectAsStateWithLifecycle()

    RatingScreenContent(
        modifier = Modifier
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