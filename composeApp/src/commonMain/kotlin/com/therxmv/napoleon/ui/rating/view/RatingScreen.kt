package com.therxmv.napoleon.ui.rating.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.therxmv.napoleon.Res

@Composable
fun RatingScreen() {
//    when (state) {
//        is RatingUiState.Ready -> RatingScreenContent(
//            modifier = modifier,
//            data = state.data,
//            inputs = inputsState,
//            onValueChange = viewModel::updateInputByIndex,
//            ratingState = ratingState,
//            calculateRating = viewModel::calculateRating,
//        )
//
//        is RatingUiState.NotAvailable -> NotAvailableBanner()
//        is RatingUiState.Loading -> ProgressIndicator()
//    }
}

@Composable
private fun NotAvailableBanner() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(36.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = Res.string.rating_not_available,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
    }
}