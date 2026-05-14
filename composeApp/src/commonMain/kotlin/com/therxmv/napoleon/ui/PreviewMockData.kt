package com.therxmv.napoleon.ui

import com.therxmv.leonres.getSyncString
import com.therxmv.leonui.input.LeonDropdownInputData
import com.therxmv.napoleon.ui.dashboard.component.DashboardUiData
import com.therxmv.napoleon.ui.editprofile.component.EditProfileUiData
import com.therxmv.napoleon.ui.exam.component.ExamsUiData
import com.therxmv.napoleon.ui.profile.component.ProfileUiData
import com.therxmv.napoleon.ui.rating.component.RatingUiData
import com.therxmv.napoleon.ui.rating.component.RatingUiData.ProbabilityInput
import com.therxmv.napoleon.ui.rating.component.RatingUiData.SubjectInput
import com.therxmv.napoleon.ui.schedule.component.ScheduleUiData
import com.therxmv.napoleon.ui.schedule.component.ScheduleUiEvent
import com.therxmv.napoleon.ui.timetable.component.TimetableUiData
import compose.icons.FeatherIcons
import compose.icons.feathericons.Clock
import compose.icons.feathericons.DivideCircle
import compose.icons.feathericons.Folder
import compose.icons.feathericons.Globe
import compose.icons.feathericons.Layout
import kotlinx.datetime.LocalDate
import napoleon.leonres.generated.resources.Res
import napoleon.leonres.generated.resources.dashboard_excel_tile
import napoleon.leonres.generated.resources.dashboard_process_tile
import napoleon.leonres.generated.resources.dashboard_rating_tile
import napoleon.leonres.generated.resources.dashboard_site_tile
import napoleon.leonres.generated.resources.dashboard_timetable_tile
import napoleon.leonres.generated.resources.edit_profile_faculty_placeholder
import napoleon.leonres.generated.resources.edit_profile_save_button
import napoleon.leonres.generated.resources.edit_profile_specialty_placeholder
import napoleon.leonres.generated.resources.edit_profile_year_placeholder
import napoleon.leonres.generated.resources.exams_edit_info
import napoleon.leonres.generated.resources.exams_edit_info_link_text
import napoleon.leonres.generated.resources.exams_list_title
import napoleon.leonres.generated.resources.profile_edit_button
import napoleon.leonres.generated.resources.profile_faculty_label
import napoleon.leonres.generated.resources.profile_info_title
import napoleon.leonres.generated.resources.profile_specialty_label
import napoleon.leonres.generated.resources.rating_add_label
import napoleon.leonres.generated.resources.rating_credits_label
import napoleon.leonres.generated.resources.rating_info_link_text
import napoleon.leonres.generated.resources.rating_info_text
import napoleon.leonres.generated.resources.rating_label
import napoleon.leonres.generated.resources.rating_name_label
import napoleon.leonres.generated.resources.rating_probability
import napoleon.leonres.generated.resources.rating_score_label
import napoleon.leonres.generated.resources.timetable_close
import napoleon.leonres.generated.resources.timetable_copy
import napoleon.leonres.generated.resources.timetable_empty_shift
import napoleon.leonres.generated.resources.timetable_first_shift
import napoleon.leonres.generated.resources.timetable_second_shift
import napoleon.leonres.generated.resources.timetable_title
import napoleon.leonres.generated.resources.zalik_list_title

object PreviewMockData {

    val dashboardUiData = DashboardUiData(
        widgets = listOf(DashboardUiData.Widget.SkeletonTodaySchedule),
        tiles = listOf(
            DashboardUiData.Tile.wideRectangle(
                icon = FeatherIcons.Layout,
                titleRes = Res.string.dashboard_excel_tile,
                onClick = {},
            ),
            DashboardUiData.Tile.smallSquare(
                icon = FeatherIcons.DivideCircle,
                titleRes = Res.string.dashboard_rating_tile,
                onClick = {},
            ),
            DashboardUiData.Tile.smallSquare(
                icon = FeatherIcons.Clock,
                titleRes = Res.string.dashboard_timetable_tile,
                onClick = {},
            ),
            DashboardUiData.Tile.EmptyDivider,
            DashboardUiData.Tile.smallRectangle(
                icon = FeatherIcons.Globe,
                titleRes = Res.string.dashboard_site_tile,
                onClick = {},
            ),
            DashboardUiData.Tile.smallRectangle(
                icon = FeatherIcons.Folder,
                titleRes = Res.string.dashboard_process_tile,
                onClick = {},
            ),
        ),
        cacheReason = "Cache Message",
    )

    val editProfileUiData = EditProfileUiData(
        facultyDropdown = LeonDropdownInputData(
            placeholderRes = Res.string.edit_profile_faculty_placeholder,
            value = "ФМІ",
            items = listOf("ФМІ"),
            onClick = {},
        ),
        yearDropdown = LeonDropdownInputData(
            placeholderRes = Res.string.edit_profile_year_placeholder,
            items = listOf("4"),
            onClick = {},
        ),
        specialtyDropdown = LeonDropdownInputData(
            placeholderRes = Res.string.edit_profile_specialty_placeholder,
            onClick = {},
        ),
        saveLabelRes = Res.string.edit_profile_save_button,
    )

    val profileUiData = ProfileUiData(
        infoTitleRes = Res.string.profile_info_title,
        facultyLabelRes = Res.string.profile_faculty_label,
        faculty = "ФМІ",
        specialtyLabelRes = Res.string.profile_specialty_label,
        specialty = "ІПЗ-41",
        editButtonLabelRes = Res.string.profile_edit_button,
    )

    val ratingUiData = RatingUiData(
        nameLabelRes = Res.string.rating_name_label,
        creditsLabelRes = Res.string.rating_credits_label,
        scoreLabelRes = Res.string.rating_score_label,
        addInputLabelRes = Res.string.rating_add_label,
        subjectInputs = List(2) { SubjectInput() },
        ratingResult = "${Res.string.rating_label} 88.14",
        probabilityInputs = listOf(
            ProbabilityInput(
                id = ProbabilityInput.Id.Capacity,
                value = "20",
            ),
            ProbabilityInput(
                id = ProbabilityInput.Id.Quota,
                value = "8",
            ),
            ProbabilityInput(
                id = ProbabilityInput.Id.Average,
                value = "75",
            ),
            ProbabilityInput(
                id = ProbabilityInput.Id.Deviation,
                value = "5",
            ),
        ),
        probabilityResult = "${Res.string.rating_probability} 78.4%",
        infoData = RatingUiData.Info(
            textRes = Res.string.rating_info_text,
            link = "link",
            linkTextRes = Res.string.rating_info_link_text,
        ),
    )

    val scheduleUiData = ScheduleUiData(
        days = listOf(
            ScheduleUiData.Day.Default(
                name = "Monday",
                lessons = listOf(
                    ScheduleUiData.Lesson.Empty(id = "1", number = "1", name = "Empty Lesson"),
                    ScheduleUiData.Lesson.Online(id = "2", number = "2", name = "Online Lesson", link = "link", onClick = {}),
                    ScheduleUiData.Lesson.Offline(id = "3", number = "3", name = "Offline Lesson", classroom = "ауд. 101"),
                    ScheduleUiData.Lesson.ByTime(id = "4", time = "12:45", name = "By Time Lesson"),
                ),
                isExpanded = true,
                expandEvent = ScheduleUiEvent.ExpandDay("Monday"),
            ),
            ScheduleUiData.Day.Default(
                name = "Tuesday",
                lessons = listOf(),
                isExpanded = false,
                expandEvent = ScheduleUiEvent.ExpandDay("Tuesday"),
            ),
            ScheduleUiData.Day.Default(
                name = "Wednesday",
                lessons = listOf(),
                isExpanded = false,
                expandEvent = ScheduleUiEvent.ExpandDay("Wednesday"),
            ),
            ScheduleUiData.Day.Empty("Thursday"),
            ScheduleUiData.Day.Empty("Friday"),
        ),
    )

    val timetableUiData = TimetableUiData(
        icon = FeatherIcons.Clock,
        titleRes = Res.string.timetable_title,
        text = buildString {
            append(getSyncString(Res.string.timetable_first_shift))
            append("\n1) 8:00 - 9:20\n2) 9:35 - 10:55")
            append("\n\n")
            append(getSyncString(Res.string.timetable_second_shift))
            append("\n")
            append(getSyncString(Res.string.timetable_empty_shift))
        },
        copyLabelRes = Res.string.timetable_copy,
        closeLabelRes = Res.string.timetable_close,
    )

    val examsUiData = ExamsUiData(
        infoData = ExamsUiData.Info(
            textRes = Res.string.exams_edit_info,
            link = "link",
            linkTextRes = Res.string.exams_edit_info_link_text,
        ),
        sections = listOf(
            ExamsUiData.Section(
                id = ExamsUiData.Section.Id.Exam,
                titleRes = Res.string.exams_list_title,
                items = listOf(
                    ExamsUiData.Item.Editable.Exam(
                        teacher = "Teacher's full name",
                        name = "This is Lesson1",
                        date = LocalDate(2025, 1, 10),
                    ),
                    ExamsUiData.Item.Editable.Exam(
                        teacher = "Teacher's full name",
                        name = "This is\nLesson2",
                        date = LocalDate(2025, 1, 16),
                        isEditing = true,
                    ),
                ),
            ),
            ExamsUiData.Section(
                id = ExamsUiData.Section.Id.Zalik,
                titleRes = Res.string.zalik_list_title,
                items = listOf(
                    ExamsUiData.Item.EmptyPlaceholder(name = "Empty Placeholder Example"),
                    ExamsUiData.Item.Editable.Zalik(name = "This is Lesson3", isEditing = true),
                    ExamsUiData.Item.Editable.Zalik(name = "This is\nLesson4"),
                ),
            )
        )
    )
}
