package com.therxmv.napoleon.navigation.destination.child

import com.arkivanov.decompose.ComponentContext
import com.therxmv.napoleon.navigation.bottom.BottomNavigationComponent
import com.therxmv.napoleon.navigation.destination.slot.SlotDestination
import com.therxmv.napoleon.navigation.fullscreen.FullScreenComponent
import com.therxmv.napoleon.ui.dashboard.component.DashboardComponent
import com.therxmv.napoleon.ui.editprofile.component.EditProfileComponent
import com.therxmv.napoleon.ui.exam.component.ExamsComponent
import com.therxmv.napoleon.ui.profile.component.ProfileComponent
import com.therxmv.napoleon.ui.rating.component.RatingComponent
import com.therxmv.napoleon.ui.schedule.component.ScheduleComponent

sealed interface Child {

    data class BottomNavigation(val component: BottomNavigationComponent, val child: Bottom) : Child

    sealed interface Bottom {
        data class Dashboard(val component: DashboardComponent) : Bottom

        data class Schedule(val component: ScheduleComponent) : Bottom

        data class Profile(val component: ProfileComponent) : Bottom
    }

    data class FullScreen(val component: FullScreenComponent, val child: Full) : Child

    sealed interface Full {
        data class EditProfile(val component: EditProfileComponent) : Full

        data class Exams(val component: ExamsComponent) : Full

        data class Rating(val component: RatingComponent) : Full
    }

    fun interface Factory {

        operator fun invoke(
            currentDestination: ChildDestination,
            componentContext: ComponentContext,
            navigateTo: (ChildDestination) -> Unit,
            activateSlot: (SlotDestination) -> Unit,
            canGoBack: () -> Boolean,
            goBack: () -> Unit,
        ): Child
    }
}