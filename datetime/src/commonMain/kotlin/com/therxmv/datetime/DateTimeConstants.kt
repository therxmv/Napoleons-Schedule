package com.therxmv.datetime

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char

object DateTimeConstants {

    object Year {
        val range = IntRange(1900, 2100)
        val list = range.toList()
    }

    object Week {
        // TODO p1 localize week names; Migrate to normal resources
        val list = DayOfWeekNames.ENGLISH_ABBREVIATED.names
        val size = list.size
    }

    object Format {
        val dayDotMonthDotYear = LocalDate.Format {
            day()
            char('.')
            monthNumber()
            char('.')
            year()
        }
        val dayDotMonthNewLineYear = LocalDate.Format {
            day()
            char('.')
            monthNumber()
            char('\n')
            year()
        }
        val fullMonthSpaceYear = LocalDate.Format {
            monthName(MonthNames.ENGLISH_FULL)
            char(' ')
            year()
        }
        val fullDate = LocalDate.Format {
            monthName(MonthNames.ENGLISH_FULL)
            char(' ')
            day()
            chars(", ")
            year()
        }
    }
}