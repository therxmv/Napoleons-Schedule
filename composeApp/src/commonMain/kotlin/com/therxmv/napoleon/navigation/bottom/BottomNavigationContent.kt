package com.therxmv.napoleon.navigation.bottom

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.therxmv.napoleon.navigation.destination.child.Child
import com.therxmv.napoleon.ui.dashboard.DashboardScreen
import com.therxmv.napoleon.ui.profile.ProfileScreen
import com.therxmv.napoleon.ui.schedule.ScheduleScreen
import com.therxmv.napoleon.ui.theme.NapoleonTheme

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
    data: BottomNavigationComponent.AppBarData,
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
        }
    )
}

@Composable
private fun AppBarAction(
    data: BottomNavigationComponent.AppBarData.Action,
) {
    IconButton(onClick = data.onClick) {
        Icon(imageVector = data.icon, contentDescription = null)
    }
}

@Composable
fun BottomNavBar(
    data: BottomNavigationComponent.Data,
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        data.tabs.forEach { data ->
            NavigationBarItem(
                colors = NapoleonTheme.colors.navBarItem,
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