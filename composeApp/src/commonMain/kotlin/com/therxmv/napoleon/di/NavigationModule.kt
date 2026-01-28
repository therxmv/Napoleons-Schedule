package com.therxmv.napoleon.di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.therxmv.napoleon.navigation.bottom.BottomNavigationComponent
import com.therxmv.napoleon.navigation.component.NavigationRootComponent
import com.therxmv.napoleon.navigation.component.RootComponent
import com.therxmv.napoleon.navigation.destination.child.Child
import com.therxmv.napoleon.navigation.destination.child.Child.Bottom
import com.therxmv.napoleon.navigation.destination.child.Child.Full
import com.therxmv.napoleon.navigation.destination.child.ChildDestination.BottomNav.Dashboard
import com.therxmv.napoleon.navigation.destination.child.ChildDestination.BottomNav.Profile
import com.therxmv.napoleon.navigation.destination.child.ChildDestination.BottomNav.Schedule
import com.therxmv.napoleon.navigation.destination.child.ChildDestination.FullScreen.CreateProfile
import com.therxmv.napoleon.navigation.destination.child.ChildDestination.FullScreen.EditProfile
import com.therxmv.napoleon.navigation.destination.child.ChildDestination.FullScreen.Exams
import com.therxmv.napoleon.navigation.destination.child.ChildDestination.FullScreen.Rating
import com.therxmv.napoleon.navigation.destination.slot.Slot
import com.therxmv.napoleon.navigation.destination.slot.SlotDestination
import com.therxmv.napoleon.navigation.fullscreen.FullScreenComponent
import com.therxmv.napoleon.ui.dashboard.component.DashboardComponent
import com.therxmv.napoleon.ui.editprofile.component.EditProfileComponent
import com.therxmv.napoleon.ui.exam.component.ExamsComponent
import com.therxmv.napoleon.ui.profile.component.ProfileComponent
import com.therxmv.napoleon.ui.rating.component.RatingComponent
import com.therxmv.napoleon.ui.schedule.component.ScheduleComponent
import com.therxmv.napoleon.ui.schedule.component.ScheduleUiConverter
import com.therxmv.napoleon.ui.timetable.component.TimetableComponent
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val navigationModule = module {
    single<ComponentContext> { DefaultComponentContext(LifecycleRegistry()) }

    singleOf(::NavigationRootComponent) bind RootComponent::class

    single<Child.Factory> {
        Child.Factory { currentDestination, context, navigateTo, activateSlot, canGoBack, goBack ->
            val bottomEntry: (Bottom) -> Child.BottomNavigation = {
                Child.BottomNavigation(
                    component = BottomNavigationComponent(context, currentDestination, navigateTo, activateSlot, get()),
                    child = it,
                )
            }
            val fullEntry: (Full) -> Child.FullScreen = {
                Child.FullScreen(
                    component = FullScreenComponent(context, currentDestination, canGoBack, goBack, get()),
                    child = it,
                )
            }

            when (currentDestination) {
                Dashboard -> bottomEntry(
                    Bottom.Dashboard(
                        DashboardComponent(
                            componentContext = context,
                            specialtyRepository = get(),
                            profileRepository = get(),
                            analyticsRepository = get(),
                            scheduleUiConverter = get(),
                            infoRepository = get(),
                            navigateTo = navigateTo,
                            activateSlot = activateSlot,
                        )
                    )
                )

                Schedule -> bottomEntry(
                    Bottom.Schedule(
                        ScheduleComponent(
                            context,
                            specialtyRepository = get(),
                            profileRepository = get(),
                            analyticsRepository = get(),
                            scheduleUiConverter = get(),
                        )
                    )
                )

                Profile -> bottomEntry(
                    Bottom.Profile(
                        ProfileComponent(
                            componentContext = context,
                            profileRepository = get(),
                            navigateTo = navigateTo,
                        )
                    )
                )

                CreateProfile, EditProfile -> fullEntry(
                    Full.EditProfile(
                        EditProfileComponent(
                            componentContext = context,
                            facultyRepository = get(),
                            profileRepository = get(),
                            currentDestination = currentDestination,
                            navigateTo = navigateTo,
                            goBack = goBack,
                            analyticsRepository = get(),
                            mainDispatcher = get(named(KoinDispatchers.Main)),
                        )
                    )
                )

                Exams -> fullEntry(
                    Full.Exams(
                        ExamsComponent(
                            componentContext = context,
                            specialtyRepository = get(),
                            profileRepository = get(),
                            infoRepository = get(),
                        )
                    )
                )

                Rating -> fullEntry(
                    Full.Rating(
                        RatingComponent(
                            componentContext = context,
                            ratingRepository = get(),
                            infoRepository = get(),
                            ioDispatcher = get(named(KoinDispatchers.IO)),
                        )
                    )
                )
            }
        }
    }

    single<Slot.Factory> {
        Slot.Factory { currentDestination, context, dismiss ->
            when (currentDestination) {
                SlotDestination.TimetableDialog -> Slot.TimetableDialogSlot(
                    TimetableComponent(
                        componentContext = context,
                        dismiss = dismiss,
                        timetableRepository = get(),
                        analyticsRepository = get(),
                    )
                )
            }
        }
    }

    single { ScheduleUiConverter(defaultDispatcher = get(named(KoinDispatchers.Default))) }
}