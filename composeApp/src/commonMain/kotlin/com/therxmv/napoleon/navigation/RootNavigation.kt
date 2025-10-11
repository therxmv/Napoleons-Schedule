package com.therxmv.napoleon.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.PredictiveBackParams
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.therxmv.napoleon.navigation.bottom.BottomNavBar
import com.therxmv.napoleon.navigation.bottom.BottomNavigationContent
import com.therxmv.napoleon.navigation.bottom.TopLeftAppBar
import com.therxmv.napoleon.navigation.component.RootComponent
import com.therxmv.napoleon.navigation.destination.child.Child
import com.therxmv.napoleon.navigation.destination.slot.Slot
import com.therxmv.napoleon.navigation.fullscreen.FullScreenContent
import com.therxmv.napoleon.navigation.fullscreen.TopCenterAppBar
import com.therxmv.napoleon.ui.timetable.TimetableDialog

@OptIn(ExperimentalDecomposeApi::class, ExperimentalComposeUiApi::class)
@Composable
fun RootNavigation(component: RootComponent) {
    ScaffoldChildStack(
        component = component,
        animation = stackAnimation(
            animator = fade(),
            predictiveBackParams = {
                PredictiveBackParams(
                    backHandler = component.backHandler,
                    onBack = component::onBackClicked,
                )
            },
        ),
    ) { paddingValues, active ->
        val isHandlerEnabled by rememberUpdatedState(component.canGoBack())
        BackHandler(enabled = isHandlerEnabled, onBack = component::onBackClicked)

        when (val child = active.instance) {
            is Child.BottomNavigation -> {
                BottomNavigationContent(
                    modifier = Modifier.padding(paddingValues),
                    child = child.child,
                )
            }

            is Child.FullScreen -> {
                FullScreenContent(
                    modifier = Modifier.padding(paddingValues),
                    child = child.child,
                )
            }
        }
    }

    SlotContent(component)
}

@OptIn(ExperimentalDecomposeApi::class)
@Composable
private fun ScaffoldChildStack(
    component: RootComponent,
    animation: StackAnimation<Any, Child>? = null,
    content: @Composable AnimatedVisibilityScope.(PaddingValues, com.arkivanov.decompose.Child.Created<Any, Child>) -> Unit,
) {
    val stack by component.stack.subscribeAsState()
    val activeChild = stack.active.instance

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .safeDrawingPadding(),
        topBar = {
            when (activeChild) {
                is Child.BottomNavigation -> TopLeftAppBar(activeChild.component.data.appBarData)
                is Child.FullScreen -> TopCenterAppBar(activeChild.component.data)
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = activeChild is Child.BottomNavigation,
                enter = slideInVertically { it },
                exit = slideOutVertically { -it },
            ) {
                when (activeChild) {
                    is Child.BottomNavigation -> BottomNavBar(activeChild.component.data)
                    else -> Unit
                }
            }
        },
    ) { paddingValues ->
        ChildStack(
            stack = stack,
            animation = animation,
            content = {
                this.content(paddingValues, it)
            },
        )
    }
}

@Composable
private fun SlotContent(
    component: RootComponent,
) {
    val slot by component.slot.subscribeAsState()
    val activeSlot = slot.child?.instance

    when (activeSlot) {
        is Slot.TimetableDialogSlot -> {
            TimetableDialog(activeSlot.component)
        }

        null -> Unit
    }
}