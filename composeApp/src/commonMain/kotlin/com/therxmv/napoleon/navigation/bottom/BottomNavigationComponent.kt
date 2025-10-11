package com.therxmv.napoleon.navigation.bottom

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import com.arkivanov.decompose.ComponentContext
import com.therxmv.napoleon.Res
import com.therxmv.napoleon.data.repository.profile.ProfileRepository
import com.therxmv.napoleon.navigation.bottom.BottomNavigationComponent.Data.Tab
import com.therxmv.napoleon.navigation.destination.child.ChildDestination
import com.therxmv.napoleon.navigation.destination.child.ChildDestination.BottomNav
import com.therxmv.napoleon.navigation.destination.slot.SlotDestination
import compose.icons.FeatherIcons
import compose.icons.feathericons.Clock
import compose.icons.feathericons.Home
import compose.icons.feathericons.List
import compose.icons.feathericons.User

class BottomNavigationComponent(
    componentContext: ComponentContext,
    private val currentDestination: ChildDestination,
    private val navigateTo: (ChildDestination) -> Unit,
    private val activateSlot: (SlotDestination) -> Unit,
    private val profileRepository: ProfileRepository,
) : ComponentContext by componentContext {

    val data = Data(
        appBarData = AppBarData(
            title = currentDestination.resolveTitle(),
            actions = currentDestination.resolveActions(),
        ),
        tabs = listOf(
            BottomNav.Dashboard.toTab(
                label = Res.string.bottom_dashboard_label,
                icon = FeatherIcons.Home,
            ),
            BottomNav.Schedule.toTab(
                label = Res.string.bottom_schedule_label,
                icon = FeatherIcons.List,
            ),
            BottomNav.Profile.toTab(
                label = Res.string.bottom_profile_label,
                icon = FeatherIcons.User,
            ),
        ),
    )

    private fun ChildDestination.toTab(label: String, icon: ImageVector): Tab =
        Tab(
            label = label,
            icon = icon,
            isSelected = currentDestination == this,
            onClick = { navigateTo(this) },
        )

    private fun ChildDestination.resolveTitle(): String =
        when (this) {
            BottomNav.Dashboard -> Res.string.app_name
            BottomNav.Schedule -> getScheduleTitle()
            BottomNav.Profile -> getProfileTitle()
            else -> Res.string.app_name
        }

    private fun getScheduleTitle(): String {
        val profile = profileRepository.getNotNullProfileSync()
        val specialty = profile.specialtyName
        val faculty = profile.facultyName

        return Res.string.schedule_title + " $specialty, $faculty"
    }

    private fun getProfileTitle(): String {
        val profile = profileRepository.getNotNullProfileSync()

        return Res.string.profile_welcome + " ${profile.name}!"
    }

    private fun ChildDestination.resolveActions(): List<AppBarData.Action> =
        when (this) {
            BottomNav.Schedule -> listOf(
                AppBarData.Action(
                    icon = FeatherIcons.Clock,
                    onClick = { activateSlot(SlotDestination.TimetableDialog) },
                ),
            )

            else -> emptyList()
        }

    @Immutable
    data class Data(
        val appBarData: AppBarData,
        val tabs: List<Tab>,
    ) {
        data class Tab(
            val label: String,
            val icon: ImageVector,
            val isSelected: Boolean,
            val onClick: () -> Unit,
        )
    }

    @Immutable
    data class AppBarData(
        val title: String,
        val actions: List<Action>,
    ) {
        data class Action(
            val icon: ImageVector,
            val onClick: () -> Unit,
        )
    }
}