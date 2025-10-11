package com.therxmv.napoleon.navigation.destination.child

import kotlinx.serialization.Serializable

@Serializable
sealed interface ChildDestination {

    @Serializable
    sealed interface BottomNav : ChildDestination {
        @Serializable
        data object Dashboard : BottomNav

        @Serializable
        data object Schedule : BottomNav

        @Serializable
        data object Profile : BottomNav
    }

    @Serializable
    sealed interface FullScreen : ChildDestination {
        @Serializable
        data object CreateProfile : FullScreen

        @Serializable
        data object EditProfile : FullScreen

        @Serializable
        data object Exams : FullScreen

        @Serializable
        data object Rating : FullScreen
    }

    val isFullScreen: Boolean
        get() = this is FullScreen

    val isBottomNav: Boolean
        get() = this is BottomNav
}