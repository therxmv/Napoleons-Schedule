package com.therxmv.datetime.picker.state

import androidx.compose.runtime.Stable
import com.therxmv.datetime.getNowDate
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.DateTimeFormat

/**
 * Represents the state and behavior of a date picker.
 */
@Stable
interface DatePickerState {

    /**
     * Current date being displayed.
     */
    val currentDate: LocalDate

    /**
     * Currently selected date by the user.
     */
    val selectedDate: LocalDate

    val currentYear: Int
    val selectedYear: Int

    /**
     * Returns a grid of month days.
     * Each sublist represents a week of [Day] objects.
     */
    fun getMonthDays(): List<List<Day>>

    fun selectDate(newDate: LocalDate)
    fun plusMonth()
    fun minusMonth()
    fun selectYear(newYear: Int)

    fun formatCurrentDate(formatter: DateTimeFormat<LocalDate>): String
    fun formatSelectedDate(formatter: DateTimeFormat<LocalDate>): String

    /**
     * A UI model for day item.
     *
     * @property date The date of this object.
     * @property isCurrentMonth Indicates whether the date belongs to the currently displayed month.
     */
    data class Day(
        val date: LocalDate,
        val isCurrentMonth: Boolean = false,
    ) {
        val number: String
            get() = date.day.toString()

        val isToday: Boolean
            get() = date == getNowDate()
    }
}