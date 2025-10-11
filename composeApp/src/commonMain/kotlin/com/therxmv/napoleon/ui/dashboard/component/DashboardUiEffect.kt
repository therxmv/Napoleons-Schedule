package com.therxmv.napoleon.ui.dashboard.component

sealed interface DashboardUiEffect {

    class OpenWebUrl(val url: String) : DashboardUiEffect
}