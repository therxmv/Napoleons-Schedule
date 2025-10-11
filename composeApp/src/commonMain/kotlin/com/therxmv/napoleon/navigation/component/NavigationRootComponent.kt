package com.therxmv.napoleon.navigation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.therxmv.napoleon.data.repository.analytics.AnalyticsRepository
import com.therxmv.napoleon.data.repository.profile.ProfileRepository
import com.therxmv.napoleon.navigation.destination.child.Child
import com.therxmv.napoleon.navigation.destination.child.ChildDestination
import com.therxmv.napoleon.navigation.destination.slot.Slot
import com.therxmv.napoleon.navigation.destination.slot.SlotDestination

class NavigationRootComponent(
    componentContext: ComponentContext,
    private val childFactory: Child.Factory,
    private val slotFactory: Slot.Factory,
    private val profileRepository: ProfileRepository,
    private val analyticsRepository: AnalyticsRepository,
) : ComponentContext by componentContext, RootComponent {

    private val navigation = StackNavigation<ChildDestination>()

    override val stack: Value<ChildStack<*, Child>> = childStack(
        source = navigation,
        serializer = ChildDestination.serializer(),
        initialConfiguration = resolveInitialDestination(),
        childFactory = { destination, context ->
            childFactory.invoke(
                currentDestination = destination,
                componentContext = context,
                navigateTo = { new ->
                    navigateTo(new = new, current = destination)
                },
                activateSlot = ::activateSlot,
                canGoBack = ::canGoBack,
                goBack = ::onBackClicked,
            )
        },
    )

    private val slotNavigation = SlotNavigation<SlotDestination>()

    override val slot: Value<ChildSlot<*, Slot>> = childSlot(
        source = slotNavigation,
        serializer = SlotDestination.serializer(),
    ) { destination, context ->
        slotFactory.invoke(
            currentDestination = destination,
            componentContext = context,
            dismiss = slotNavigation::dismiss,
        )
    }

    override fun onBackClicked() {
        when {
            slot.value.child != null -> slotNavigation.dismiss()

            else -> navigation.pop()
        }
    }

    override fun canGoBack(): Boolean = stack.value.backStack.isNotEmpty()

    private fun navigateTo(new: ChildDestination, current: ChildDestination) {
        when {
            current == new -> return

            new.isFullScreen -> navigation.pushNew(new)

            new.isBottomNav -> navigation.replaceAll(new)
        }

        analyticsRepository.reportNavigation(new.toString())
    }

    private fun activateSlot(new: SlotDestination) {
        slotNavigation.activate(new)
    }

    private fun resolveInitialDestination(): ChildDestination =
        when {
            profileRepository.isLoggedIn.not() -> ChildDestination.FullScreen.CreateProfile

            else -> ChildDestination.BottomNav.Dashboard
        }
}