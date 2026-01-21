package com.therxmv.napoleon.navigation.bottom

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.therxmv.leonui.button.LeonIconButton
import com.therxmv.leonui.theme.LeonPreview
import com.therxmv.leonui.theme.LeonTheme
import com.therxmv.napoleon.Res
import com.therxmv.napoleon.navigation.bottom.BottomNavigationComponent.AppBarData
import com.therxmv.napoleon.navigation.bottom.BottomNavigationComponent.Data
import com.therxmv.napoleon.navigation.destination.child.Child
import com.therxmv.napoleon.ui.dashboard.DashboardScreen
import com.therxmv.napoleon.ui.profile.ProfileScreen
import com.therxmv.napoleon.ui.schedule.ScheduleScreen
import compose.icons.FeatherIcons
import compose.icons.feathericons.Clock
import compose.icons.feathericons.Home
import compose.icons.feathericons.List
import compose.icons.feathericons.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationContent(
    modifier: Modifier = Modifier,
    child: Child.Bottom,
) {
    when (child) {
        is Child.Bottom.Dashboard -> {
            DashboardScreen(
                modifier = modifier,
                component = child.component,
            )
        }

        is Child.Bottom.Schedule -> {
            ScheduleScreen(
                modifier = modifier,
                component = child.component,
            )
        }

        is Child.Bottom.Profile -> {
            ProfileScreen(
                modifier = modifier,
                component = child.component,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopLeftAppBar(
    data: AppBarData,
    windowInsets: WindowInsets,
) {
    TopAppBar(
        title = {
            Text(
                text = data.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        },
        actions = {
            data.actions.forEach {
                AppBarAction(it)
            }
        },
        windowInsets = windowInsets,
    )
}

@Composable
private fun AppBarAction(
    data: AppBarData.Action,
) {
    LeonIconButton(
        icon = data.icon,
        onClick = data.onClick,
    )
}

@Composable
fun BottomNavBar(
    data: Data,
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = LeonTheme.colors.surface,
    ) {
        data.tabs.forEach { data ->
            NavigationBarItem(
                colors = NavigationBarItemColors(
                    selectedIconColor = LeonTheme.colors.onPrimary,
                    selectedTextColor = LeonTheme.colors.onSurface,
                    selectedIndicatorColor = LeonTheme.colors.primary,
                    unselectedIconColor = LeonTheme.colors.onSurface,
                    unselectedTextColor = LeonTheme.colors.onSurface,
                    disabledIconColor = LeonTheme.colors.onSurface,
                    disabledTextColor = LeonTheme.colors.onSurface
                ),
                selected = data.isSelected,
                onClick = data.onClick,
                icon = {
                    Icon(imageVector = data.icon, contentDescription = data.label)
                },
                label = {
                    Text(
                        text = data.label,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                },
            )
        }
    }
}

@Preview
@Composable
private fun TopLeftAppBarPreview() {
    LeonPreview {
        TopLeftAppBar(
            data = appBarData,
            windowInsets = WindowInsets.safeDrawing,
        )
    }
}

@Preview
@Composable
private fun BottomNavBarPreview() {
    LeonPreview {
        BottomNavBar(
            data = Data(
                appBarData = appBarData,
                tabs = listOf(
                    Data.Tab(
                        label = Res.string.bottom_dashboard_label,
                        icon = FeatherIcons.Home,
                        isSelected = true,
                        onClick = {},
                    ),
                    Data.Tab(
                        label = Res.string.bottom_schedule_label,
                        icon = FeatherIcons.List,
                        isSelected = false,
                        onClick = {},
                    ),
                    Data.Tab(
                        label = Res.string.bottom_profile_label,
                        icon = FeatherIcons.User,
                        isSelected = false,
                        onClick = {},
                    ),
                ),
            )
        )
    }
}

private val appBarData = AppBarData(
    title = "TopLeftAppBar",
    actions = listOf(
        AppBarData.Action(
            icon = FeatherIcons.Clock,
            onClick = {},
        )
    ),
)