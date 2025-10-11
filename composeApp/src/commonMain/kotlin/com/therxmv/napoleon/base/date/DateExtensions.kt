@file:OptIn(ExperimentalTime::class)

package com.therxmv.napoleon.base.date

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

fun getNowMillis(): Long =
    Clock.System.now().toEpochMilliseconds()

fun getTodayDateTime(): LocalDateTime =
    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())