package com.therxmv.napoleon.navigation.bottom

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import com.arkivanov.decompose.ComponentContext
import com.therxmv.leonres.getSyncString
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
import napoleon.leonres.generated.resources.Res
import napoleon.leonres.generated.resources.app_name
import napoleon.leonres.generated.resources.bottom_dashboard_label
import napoleon.leonres.generated.resources.bottom_profile_label
import napoleon.leonres.generated.resources.bottom_schedule_label
import napoleon.leonres.generated.resources.profile_welcome
import napoleon.leonres.generated.resources.schedule_title
import org.jetbrains.compose.resources.StringResource

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
                labelRes = Res.string.bottom_dashboard_label,
                icon = FeatherIcons.Home,
            ),
            BottomNav.Schedule.toTab(
                labelRes = Res.string.bottom_schedule_label,
                icon = FeatherIcons.List,
            ),
            BottomNav.Profile.toTab(
                labelRes = Res.string.bottom_profile_label,
                icon = FeatherIcons.User,
            ),
        ),
    )

    private fun ChildDestination.toTab(labelRes: StringResource, icon: ImageVector): Tab =
        Tab(
            labelRes = labelRes,
            icon = icon,
            isSelected = currentDestination == this,
            onClick = { navigateTo(this) },
        )

    private fun ChildDestination.resolveTitle(): String =
        when (this) {
            BottomNav.Dashboard -> getSyncString(Res.string.app_name)
            BottomNav.Schedule -> getScheduleTitle()
            BottomNav.Profile -> getProfileTitle()
            else -> getSyncString(Res.string.app_name)
        }

    private fun getScheduleTitle(): String {
        val profile = profileRepository.getNotNullProfileSync()
        val specialty = profile.specialtyName
        val faculty = profile.facultyName

        return getSyncString(Res.string.schedule_title, specialty, faculty)
    }

    private fun getProfileTitle(): String {
        val profile = profileRepository.getNotNullProfileSync()

        return getSyncString(Res.string.profile_welcome, profile.name)
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
            val labelRes: StringResource,
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