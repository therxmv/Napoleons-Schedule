package com.therxmv.napoleon.data.source.remote.mock

import com.therxmv.napoleon.data.source.remote.napoleon.dto.ExamItemDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.ExamsDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.FacultiesDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.FacultyDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.LessonDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.ScheduleDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.ShiftDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.SpecialtiesDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.SpecialtyDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.TimetableDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.ZalikItemDto

object MockData {
    val facultiesDto = FacultiesDto(
        allFaculties = listOf(
            FacultyDto("ФМІ", "fmi_schedule"),
            FacultyDto("ППФ", "ppf_schedule"),
        )
    )

    val specialtiesDto = SpecialtiesDto(
        allYears = listOf(
            listOf(),
            listOf(),
            listOf(SpecialtyDto(specialtyName = "ІПЗ-31")),
            listOf(),
            listOf()
        )
    )

    val timetableDto = TimetableDto(
        firstShift = ShiftDto(
            time = listOf("8:00 - 9:20", "9:35 - 10:55", "11:10 - 12:30", "12:45 - 14:05"),
        ),
        secondShift = ShiftDto(
            time = listOf("12:45 - 14:05", "14:15 - 15:35", "15:45 - 17:05", "17:15 - 18:35")
        ),
    )

    val scheduleDto = ScheduleDto(
        week = listOf(
            listOf(
                LessonDto(lessonName = null, lessonNumber = "1", link = null),
                LessonDto(lessonName = "Інтернет речей доц. Викладач Н.В.", lessonNumber = "2", link = "https://meet.google.com/aam-bmke-eon"),
                LessonDto(lessonName = "Операційні системи ст.в. Викладач Т.Г.", lessonNumber = "2", link = "https://meet.google.com/aam-bmke-eon"),
                LessonDto(lessonName = "Операційні системи ст.в. Викладач Т.Г.", lessonNumber = "3", link = "https://meet.google.com/aam-bmke-eon"),
                LessonDto(lessonName = "Інтернет речей доц. Викладач Н.В.", lessonNumber = "3", link = "https://meet.google.com/aam-bmke-eon"),
            ),
            listOf(
                LessonDto(lessonName = "Системний аналіз та теорія прийняття рішень доц. Викладач І.П.", lessonNumber = "1", link = "ауд. 101"),
                LessonDto(lessonName = "Системний аналіз та теорія прийняття рішень доц. Викладач І.П.", lessonNumber = "2", link = "ауд. 101"),
                LessonDto(lessonName = "Інтернет речей доц. Викладач Н.В.", lessonNumber = "3", link = "ауд. 105"),
                LessonDto(lessonName = "Інтернет речей доц. Викладач Н.В.", lessonNumber = "4", link = "ауд. 105"),
            ),
            listOf(),
            listOf(
                LessonDto(lessonName = "Операційні системи ст.в. Викладач Т.Г.", lessonNumber = null, link = "11:45"),
                LessonDto(lessonName = "Операційні системи ст.в. Викладач Т.Г.", lessonNumber = null, link = "12:30"),
                LessonDto(lessonName = "Системний аналіз та теорія прийняття рішень доц. Викладач І.П.", lessonNumber = null, link = "13:10"),
                LessonDto(lessonName = "Інтернет речей доц. Викладач Н.В.", lessonNumber = null, link = "17:20")
            ),
            listOf(
                LessonDto(lessonName = "Системне програмування ст.в. Викладач Т.Г.", lessonNumber = "1", link = null),
                LessonDto(lessonName = "Адміністрування баз даних проф. Викладач Ю.В.", lessonNumber = "1", link = "https://meet.google.com/xyc-chmy-yhh"),
                LessonDto(lessonName = "Адміністрування баз даних проф. Викладач Ю.В.", lessonNumber = "2", link = "https://meet.google.com/xyc-chmy-yhh"),
                LessonDto(lessonName = "Системне програмування ст.в. Викладач Т.Г.", lessonNumber = "2", link = null),
            ),
        )
    )

    val examsDto = ExamsDto(
        exams = listOf(
            ExamItemDto(
                teacher = "проф. Викладач Ю.В.",
                lesson = "Якість і тестування програмного забезпечення",
                date = "25.11",
            ),
            ExamItemDto(
                teacher = "доц. Викладач О.Б.",
                lesson = "Економіка і право в IT-галузі",
                date = "19.11",
            ),
            ExamItemDto(
                teacher = "в. Викладач В.О",
                lesson = "Конструювання програмного забезпечення",
                date = "29.11",
            ),
        ),
        zalik = listOf(
            ZalikItemDto(
                lesson = "Курсова робота",
            ),
            ZalikItemDto(
                lesson = "Технологічна практика",
            ),
            ZalikItemDto(
                lesson = "Основи стандартизації та патентознавства",
            ),
            ZalikItemDto(
                lesson = "Іноземна мова для науково-дослідної комунікації",
            ),
            ZalikItemDto(
                lesson = "Хмарні обчислення та технології",
            ),
        )
    )
}