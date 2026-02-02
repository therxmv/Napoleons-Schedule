package com.therxmv.datetime.picker.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import com.therxmv.datetime.DateTimeConstants.Week
import com.therxmv.datetime.getNowDate
import com.therxmv.datetime.getNowMillis
import com.therxmv.datetime.picker.state.DatePickerState.Day
import com.therxmv.datetime.toDate
import com.therxmv.datetime.toMillis
import com.therxmv.datetime.withYear
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.minusMonth
import kotlinx.datetime.onDay
import kotlinx.datetime.plus
import kotlinx.datetime.plusMonth

/**
 * Creates a [DatePickerState] that is remembered across compositions.
 */
@Composable
fun rememberDatePickerState(
    currentDateMillis: Long = getNowMillis(),
): DatePickerState {
    return rememberSaveable(
        saver = DatePickerStateImpl.Saver(),
        init = { DatePickerStateImpl(currentDateMillis) },
    )
}

@Stable
private class DatePickerStateImpl(
    currentDateMillis: Long,
    selectedDateMillis: Long = currentDateMillis,
) : DatePickerState {

    private val _currentDate = mutableStateOf(currentDateMillis.toDate())
    override val currentDate: LocalDate
        get() = _currentDate.value

    private val _selectedDate = mutableStateOf(selectedDateMillis.toDate())
    override val selectedDate: LocalDate
        get() = _selectedDate.value

    override val currentYear: Int
        get() = getNowDate().year

    override val selectedYear: Int
        get() = selectedDate.year

    override fun getMonthDays(): List<List<Day>> =
        buildList {
            val yearMonth = YearMonth(currentDate.year, currentDate.month)

            fillPreviousMonth(yearMonth)
            fillCurrentMonth(yearMonth)
            fillNextMonth(yearMonth)
        }.chunked(Week.size)

    override fun selectDate(newDate: LocalDate) {
        _selectedDate.value = newDate
    }

    override fun plusMonth() {
        _currentDate.value = currentDate.plus(DatePeriod(months = 1))
    }

    override fun minusMonth() {
        _currentDate.value = currentDate.minus(DatePeriod(months = 1))
    }

    override fun selectYear(newYear: Int) {
        _currentDate.value = currentDate.withYear(newYear)
        _selectedDate.value = selectedDate.withYear(newYear)
    }

    override fun formatCurrentDate(formatter: DateTimeFormat<LocalDate>): String =
        currentDate.format(formatter)

    override fun formatSelectedDate(formatter: DateTimeFormat<LocalDate>): String =
        selectedDate.format(formatter)

    companion object {
        fun Saver(): Saver<DatePickerStateImpl, Any> =
            listSaver(
                save = {
                    listOf(
                        it.currentDate.toMillis(),
                        it.selectedDate.toMillis(),
                    )
                },
                restore = { value ->
                    DatePickerStateImpl(
                        currentDateMillis = value[0],
                        selectedDateMillis = value[1],
                    )
                },
            )
    }

    /**
     * 1. Finds on which day of the week current month begins (2 for Tuesday).
     * 2. Gets previous month and it's length.
     * 3. Iterate from 2 to `firstDayOfWeek`, adding the Day objects to the list.
     */
    private fun MutableList<Day>.fillPreviousMonth(currentMonth: YearMonth) {
        val firstDayOfWeek = currentMonth.onDay(1).dayOfWeek.isoDayNumber

        val previousMonth = currentMonth.minusMonth()
        val daysInPreviousMonth = previousMonth.numberOfDays

        for (i in 2..firstDayOfWeek) {
            Day(
                date = previousMonth.onDay(daysInPreviousMonth - firstDayOfWeek + i),
            ).also(::add)
        }
    }

    /**
     * 1. Gets current month length.
     * 2. Iterate from 1 to the month length, adding the Day objects to the list.
     */
    private fun MutableList<Day>.fillCurrentMonth(currentMonth: YearMonth) {
        val daysInMonth = currentMonth.numberOfDays

        for (i in 1..daysInMonth) {
            Day(
                date = currentMonth.onDay(i),
                isCurrentMonth = true,
            ).also(::add)
        }
    }

    /**
     * 1. Gets current number of filled days in the list.
     * 2. If `daysFilled` is not divisible by the `Week.size`, computes the minimum slots by rounding up to the next full week.
     *    Otherwise, uses `daysFilled` as the `totalSlots` and next step is skipped.
     * 3. Iterates from `daysFilled` until `totalSlots`, adding the Day objects to the list.
     */
    private fun MutableList<Day>.fillNextMonth(currentMonth: YearMonth) {
        val daysFilled = this.size

        val nextMonth = currentMonth.plusMonth()
        val totalSlots = if (daysFilled % Week.size != 0) {
            (daysFilled / Week.size).plus(1).times(Week.size)
        } else daysFilled

        for (i in daysFilled until totalSlots) {
            Day(
                date = nextMonth.onDay(i - daysFilled + 1),
            ).also(::add)
        }
    }
}