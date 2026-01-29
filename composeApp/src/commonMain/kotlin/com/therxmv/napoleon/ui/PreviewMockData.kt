package com.therxmv.napoleon.ui

import com.therxmv.leonui.input.LeonDropdownInputData
import com.therxmv.napoleon.Res
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

object PreviewMockData {

    val dashboardUiData = DashboardUiData(
        widgets = listOf(DashboardUiData.Widget.SkeletonTodaySchedule),
        tiles = listOf(
            DashboardUiData.Tile.wideRectangle(
                icon = FeatherIcons.Layout,
                title = Res.string.dashboard_excel_tile,
                onClick = {},
            ),
            DashboardUiData.Tile.smallSquare(
                icon = FeatherIcons.DivideCircle,
                title = Res.string.dashboard_rating_tile,
                onClick = {},
            ),
            DashboardUiData.Tile.smallSquare(
                icon = FeatherIcons.Clock,
                title = Res.string.dashboard_timetable_tile,
                onClick = {},
            ),
            DashboardUiData.Tile.EmptyDivider,
            DashboardUiData.Tile.smallRectangle(
                icon = FeatherIcons.Globe,
                title = Res.string.dashboard_site_tile,
                onClick = {},
            ),
            DashboardUiData.Tile.smallRectangle(
                icon = FeatherIcons.Folder,
                title = Res.string.dashboard_process_tile,
                onClick = {},
            ),
        ),
        cacheReason = "Cache Message",
    )

    val editProfileUiData = EditProfileUiData(
        facultyDropdown = LeonDropdownInputData(
            placeholder = Res.string.edit_profile_faculty_placeholder,
            value = "ФМІ",
            items = listOf("ФМІ"),
            onClick = {},
        ),
        yearDropdown = LeonDropdownInputData(
            placeholder = Res.string.edit_profile_year_placeholder,
            items = listOf("4"),
            onClick = {},
        ),
        specialtyDropdown = LeonDropdownInputData(
            placeholder = Res.string.edit_profile_specialty_placeholder,
            onClick = {},
        ),
        saveLabel = Res.string.edit_profile_save_button,
    )

    val profileUiData = ProfileUiData(
        infoTitle = Res.string.profile_info_title,
        facultyLabel = Res.string.profile_faculty_label,
        faculty = "ФМІ",
        specialtyLabel = Res.string.profile_specialty_label,
        specialty = "ІПЗ-41",
        editButtonLabel = Res.string.profile_edit_button,
    )

    val ratingUiData = RatingUiData(
        nameLabel = Res.string.rating_name_label,
        creditsLabel = Res.string.rating_credits_label,
        scoreLabel = Res.string.rating_score_label,
        addInputLabel = Res.string.rating_add_label,
        subjectInputs = List(2) { SubjectInput() },
        ratingResult = "${Res.string.rating_label} 88.14",
        probabilityInputs = listOf(
            ProbabilityInput(
                title = ProbabilityInput.Id.Capacity.title,
                value = "20",
            ),
            ProbabilityInput(
                title = ProbabilityInput.Id.Quota.title,
                value = "8",
            ),
            ProbabilityInput(
                title = ProbabilityInput.Id.Average.title,
                value = "75",
            ),
            ProbabilityInput(
                title = ProbabilityInput.Id.Deviation.title,
                value = "5",
            ),
        ),
        probabilityResult = "${Res.string.rating_probability} 78.4%",
        infoData = RatingUiData.Info(
            text = Res.string.rating_info_text,
            link = "link",
            linkText = Res.string.rating_info_link_text,
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
        title = Res.string.timetable_title,
        text = buildString {
            append(Res.string.timetable_first_shift)
            append("\n1) 8:00 - 9:20\n2) 9:35 - 10:55")
            append("\n\n")
            append(Res.string.timetable_second_shift)
            append("\n")
            append(Res.string.timetable_empty_shift)
        },
        copyLabel = Res.string.timetable_copy,
        closeLabel = Res.string.timetable_close,
    )

    val examsUiData = ExamsUiData(
        infoData = ExamsUiData.Info(
            text = Res.string.exams_edit_info,
            link = "link",
            linkText = Res.string.exams_edit_info_link_text,
        ),
        sections = listOf(
            ExamsUiData.Section(
                id = ExamsUiData.Section.EXAM_ID,
                title = Res.string.exams_list_title,
                items = listOf(
                    ExamsUiData.Item.Editable.Exam(
                        teacher = "Teacher's full name",
                        name = "This is Lesson1",
                        date = "19.11",
                    ),
                    ExamsUiData.Item.Editable.Exam(
                        teacher = "Teacher's full name",
                        name = "This is\nLesson2",
                        date = "25.11",
                        isEditing = true,
                    ),
                ),
            ),
            ExamsUiData.Section(
                id = ExamsUiData.Section.ZALIK_ID,
                title = Res.string.zalik_list_title,
                items = listOf(
                    ExamsUiData.Item.EmptyPlaceholder(name = "Empty Placeholder Example"),
                    ExamsUiData.Item.Editable.Zalik(name = "This is Lesson3", isEditing = true),
                    ExamsUiData.Item.Editable.Zalik(name = "This is\nLesson4"),
                ),
            )
        )
    )
}
