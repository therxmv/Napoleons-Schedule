package com.therxmv.datetime

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.char

object DateTimeConstants {

    object Year {
        val range = IntRange(1900, 2100)
        val list = range.toList()
    }

    object Week {
        // TODO localize week names; Migrate to normal resources
        val list = DayOfWeekNames.ENGLISH_ABBREVIATED.names
        val size = list.size
    }

    object Format {
        val dayMonthFormat = LocalDate.Format {
            day()
            char('.')
            monthNumber()
        }
    }
}