@file:OptIn(ExperimentalTime::class)

package com.therxmv.datetime

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

fun getNowInstant(): Instant =
    Clock.System.now()

fun getNowMillis(): Long =
    getNowInstant().toEpochMilliseconds()

fun getNowDateTime(): LocalDateTime =
    getNowInstant().toLocalDateTime(TimeZone.currentSystemDefault())

fun getNowDate(): LocalDate =
    getNowDateTime().date

fun Long.toDateTime(): LocalDateTime =
    Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())

fun Long.toDate(): LocalDate =
    toDateTime().date

fun LocalDate.toMillis(): Long =
    atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()

fun LocalDate.withYear(year: Int): LocalDate =
    if (isLeapYear(year)) {
        LocalDate(year, month, day.coerceAtMost(28))
    } else {
        LocalDate(year, month, day)
    }

fun isLeapYear(year: Int): Boolean =
    (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0)