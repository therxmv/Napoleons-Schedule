package com.therxmv.datetime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import napoleon.leonres.generated.resources.Res
import napoleon.leonres.generated.resources.datepicker_month_apr
import napoleon.leonres.generated.resources.datepicker_month_aug
import napoleon.leonres.generated.resources.datepicker_month_dec
import napoleon.leonres.generated.resources.datepicker_month_feb
import napoleon.leonres.generated.resources.datepicker_month_jan
import napoleon.leonres.generated.resources.datepicker_month_jul
import napoleon.leonres.generated.resources.datepicker_month_jun
import napoleon.leonres.generated.resources.datepicker_month_mar
import napoleon.leonres.generated.resources.datepicker_month_may
import napoleon.leonres.generated.resources.datepicker_month_nov
import napoleon.leonres.generated.resources.datepicker_month_oct
import napoleon.leonres.generated.resources.datepicker_month_sep
import napoleon.leonres.generated.resources.datepicker_weekday_fri
import napoleon.leonres.generated.resources.datepicker_weekday_mon
import napoleon.leonres.generated.resources.datepicker_weekday_sat
import napoleon.leonres.generated.resources.datepicker_weekday_sun
import napoleon.leonres.generated.resources.datepicker_weekday_thu
import napoleon.leonres.generated.resources.datepicker_weekday_tue
import napoleon.leonres.generated.resources.datepicker_weekday_wed
import org.jetbrains.compose.resources.stringResource

/**
 * Localized weekday-abbreviation labels for a calendar grid header, Monday-first (matches kotlinx-datetime's
 * isoDayNumber ordering used by [com.therxmv.datetime.picker.state.DatePickerState]).
 */
@Composable
fun rememberWeekdayLabels(): List<String> {
    val weekdayLabelRes = listOf(
        Res.string.datepicker_weekday_mon,
        Res.string.datepicker_weekday_tue,
        Res.string.datepicker_weekday_wed,
        Res.string.datepicker_weekday_thu,
        Res.string.datepicker_weekday_fri,
        Res.string.datepicker_weekday_sat,
        Res.string.datepicker_weekday_sun,
    )
    return weekdayLabelRes.map { stringResource(it) }
}

@Composable
private fun rememberLocalizedMonthNames(): MonthNames {
    val monthLabelRes = listOf(
        Res.string.datepicker_month_jan,
        Res.string.datepicker_month_feb,
        Res.string.datepicker_month_mar,
        Res.string.datepicker_month_apr,
        Res.string.datepicker_month_may,
        Res.string.datepicker_month_jun,
        Res.string.datepicker_month_jul,
        Res.string.datepicker_month_aug,
        Res.string.datepicker_month_sep,
        Res.string.datepicker_month_oct,
        Res.string.datepicker_month_nov,
        Res.string.datepicker_month_dec,
    )
    val names = monthLabelRes.map { stringResource(it) }
    return remember(names) { MonthNames(names) }
}

/** "Month Day, Year" format (e.g. "August 31, 2026") with a localized month name. */
@Composable
fun rememberFullDateFormat(): DateTimeFormat<LocalDate> {
    val monthNames = rememberLocalizedMonthNames()
    return remember(monthNames) {
        LocalDate.Format {
            monthName(monthNames)
            char(' ')
            day()
            chars(", ")
            year()
        }
    }
}

/** "Month Year" format (e.g. "August 2026") with a localized month name. */
@Composable
fun rememberMonthYearFormat(): DateTimeFormat<LocalDate> {
    val monthNames = rememberLocalizedMonthNames()
    return remember(monthNames) {
        LocalDate.Format {
            monthName(monthNames)
            char(' ')
            year()
        }
    }
}
