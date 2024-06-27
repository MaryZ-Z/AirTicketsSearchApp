package com.android.airticketssearchapp.extensions


import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

fun LocalDate.monthNameWithDay() = "$dayOfMonth ${month.abbreviation()}"

fun LocalDate.fullMonthNameWithDay() = "$dayOfMonth ${month.full()}".lowercase()

fun Month.full(): String =
    getDisplayName(TextStyle.FULL, Locale.getDefault()).trimEnd('.')

fun Month.abbreviation(): String =
    getDisplayName(TextStyle.SHORT, Locale.getDefault()).trimEnd('.')

fun DayOfWeek.abbreviationWeek(): String =
    getDisplayName(TextStyle.SHORT, Locale.getDefault()).trimEnd('.')