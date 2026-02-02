package com.therxmv.napoleon.ui.dashboard.component

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.therxmv.datetime.getNowDate
import com.therxmv.napoleon.Res
import com.therxmv.napoleon.data.repository.analytics.AnalyticsEvents
import com.therxmv.napoleon.data.repository.analytics.AnalyticsRepository
import com.therxmv.napoleon.data.repository.info.InfoRepository
import com.therxmv.napoleon.data.repository.profile.ProfileRepository
import com.therxmv.napoleon.data.repository.specialty.SpecialtyRepository
import com.therxmv.napoleon.data.repository.specialty.model.ScheduleModel
import com.therxmv.napoleon.data.source.remote.result.Result
import com.therxmv.napoleon.navigation.destination.child.ChildDestination
import com.therxmv.napoleon.navigation.destination.slot.SlotDestination
import com.therxmv.napoleon.ui.schedule.component.ScheduleUiConverter
import com.therxmv.napoleon.ui.schedule.component.ScheduleUiData
import compose.icons.FeatherIcons
import compose.icons.feathericons.Calendar
import compose.icons.feathericons.Clock
import compose.icons.feathericons.DivideCircle
import compose.icons.feathericons.Folder
import compose.icons.feathericons.Globe
import compose.icons.feathericons.Layout
import compose.icons.feathericons.MessageCircle
import compose.icons.feathericons.Send
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Stable
class DashboardComponent(
    private val componentContext: ComponentContext,
    private val specialtyRepository: SpecialtyRepository,
    private val profileRepository: ProfileRepository,
    private val analyticsRepository: AnalyticsRepository,
    private val scheduleUiConverter: ScheduleUiConverter,
    private val infoRepository: InfoRepository,
    private val navigateTo: (ChildDestination) -> Unit,
    private val activateSlot: (SlotDestination) -> Unit,
) : ComponentContext by componentContext {
    private val scope = coroutineScope(SupervisorJob())

    private val _uiEffect = Channel<DashboardUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    private val _uiState = MutableStateFlow(generateData())
    val uiState = _uiState.asStateFlow()

    init {
        loadSchedule()
    }

    fun onEvent(event: DashboardUiEvent) {
        when (event) {
            is DashboardUiEvent.Navigate -> navigateTo(event.destination)

            is DashboardUiEvent.OpenDialog -> activateSlot(event.destination)

            DashboardUiEvent.CopyDay -> analyticsRepository.reportEvent(AnalyticsEvents.COPY_SCHEDULE)

            DashboardUiEvent.CopyLessonLink -> analyticsRepository.reportEvent(AnalyticsEvents.COPY_ONLINE_LINK)
        }
    }

    private fun generateData(): DashboardUiData =
        DashboardUiData(
            widgets = generateWidgets(),
            tiles = generateTiles(),
        )

    private fun generateWidgets(): List<DashboardUiData.Widget> =
        listOf(DashboardUiData.Widget.SkeletonTodaySchedule)

    private fun generateTiles(): List<DashboardUiData.Tile> {
        val links = infoRepository.getLinks()

        return buildList {
            if (links.excelSchedule != null) {
                excelTile(links.excelSchedule).also(::add)
            }

            examsTile().also(::add)
            timetableTile().also(::add)

            ratingTile().also(::add)

            add(DashboardUiData.Tile.EmptyDivider)
            siteTile(links.mainSite).also(::add)
            processTile(links.studyProcess).also(::add)

            add(DashboardUiData.Tile.EmptyDivider)
            tgChannelTile(links.telegramChannel).also(::add)
            tgBotTile(links.telegramBot).also(::add)
        }
    }

    private fun excelTile(url: String): DashboardUiData.Tile =
        DashboardUiData.Tile.wideRectangle(
            icon = FeatherIcons.Layout,
            title = Res.string.dashboard_excel_tile,
            onClick = { openUrl(url) },
        )

    private fun examsTile(): DashboardUiData.Tile =
        DashboardUiData.Tile.smallSquare(
            icon = FeatherIcons.Calendar,
            title = Res.string.dashboard_exams_tile,
            onClick = {
                onEvent(DashboardUiEvent.Navigate(ChildDestination.FullScreen.Exams))
            },
        )

    private fun ratingTile(): DashboardUiData.Tile =
        DashboardUiData.Tile.wideRectangle(
            icon = FeatherIcons.DivideCircle,
            title = Res.string.dashboard_rating_tile,
            onClick = {
                onEvent(DashboardUiEvent.Navigate(ChildDestination.FullScreen.Rating))
            },
        )

    private fun timetableTile(): DashboardUiData.Tile =
        DashboardUiData.Tile.smallSquare(
            icon = FeatherIcons.Clock,
            title = Res.string.dashboard_timetable_tile,
            onClick = {
                onEvent(DashboardUiEvent.OpenDialog(SlotDestination.TimetableDialog))
            },
        )

    private fun tgChannelTile(url: String): DashboardUiData.Tile =
        DashboardUiData.Tile.smallRectangle(
            icon = FeatherIcons.Send,
            title = Res.string.dashboard_tg_channel_tile,
            onClick = { openUrl(url) },
        )

    private fun tgBotTile(url: String): DashboardUiData.Tile =
        DashboardUiData.Tile.smallRectangle(
            icon = FeatherIcons.MessageCircle,
            title = Res.string.dashboard_tg_bot_tile,
            onClick = { openUrl(url) },
        )

    private fun siteTile(url: String): DashboardUiData.Tile =
        DashboardUiData.Tile.smallRectangle(
            icon = FeatherIcons.Globe,
            title = Res.string.dashboard_site_tile,
            onClick = { openUrl(url) },
        )

    private fun processTile(url: String): DashboardUiData.Tile =
        DashboardUiData.Tile.smallRectangle(
            icon = FeatherIcons.Folder,
            title = Res.string.dashboard_process_tile,
            onClick = { openUrl(url) },
        )

    private fun openUrl(url: String) {
        scope.launch {
            _uiEffect.send(DashboardUiEffect.OpenWebUrl(url))
        }
    }

    private fun loadSchedule() {
        scope.launch {
            val profile = profileRepository.getNotNullProfileSync()

            val result = specialtyRepository.getSchedule(profile)

            when (result) {
                is Result.Success<ScheduleModel> -> {
                    val days = scheduleUiConverter.modelToUiData(result.data, ::openLessonUrl).days
                    val today = days.getOrNull(indexOfToday()) ?: days.firstOrNull()

                    if (today != null) {
                        val expandToday = (today as? ScheduleUiData.Day.Default)?.copy(isExpanded = true) ?: today
                        val scheduleWidget = DashboardUiData.Widget.TodaySchedule(expandToday)
                        _uiState.update { data ->
                            data.copy(
                                widgets = data.widgets.map { widget ->
                                    scheduleWidget.takeIf { widget.isSchedule } ?: widget
                                },
                                cacheReason = result.reason?.message,
                            )
                        }
                    }
                }

                is Result.Failure -> {
                    _uiState.update { data ->
                        data.copy(
                            widgets = data.widgets.filter { it.isSchedule.not() },
                        )
                    }
                }
            }
        }
    }

    private fun indexOfToday(): Int =
        getNowDate().dayOfWeek.ordinal

    private fun openLessonUrl(url: String) {
        scope.launch {
            _uiEffect.send(DashboardUiEffect.OpenWebUrl(url))
        }
        analyticsRepository.reportEvent(AnalyticsEvents.OPEN_ONLINE_LESSON)
    }
}