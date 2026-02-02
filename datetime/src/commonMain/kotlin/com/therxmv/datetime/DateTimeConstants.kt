package com.therxmv.datetime

import kotlinx.datetime.DayOfWeek

object DateTimeConstants {

    object Year {
        val range = IntRange(1900, 2100)
        val list = range.toList()
    }

    object Week {
        val list = DayOfWeek.entries.toList()
        val size = list.size
    }
}