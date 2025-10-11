package com.therxmv.napoleon.ui.rating.viewmodel.utils

import com.therxmv.napoleon.data.repository.model.RatingModel

sealed class RatingUiState {
    data object Loading : RatingUiState()
    data class Ready(val data: RatingModel) : RatingUiState()
    data object NotAvailable : RatingUiState()
}