package com.devflowteam.core.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TimeFormatManager {
    private val locale = Locale.getDefault()
    private val defaultFormat = SimpleDateFormat("yyyy-dd-MM", locale)

    fun transformFromMillis(dateInMillis: Long): String {
        val date = Date(dateInMillis)

        return defaultFormat.format(date)
    }

    fun transform(currentDate: String, format: Format): String {
        val date = defaultFormat.parse(currentDate)
        val formatted = format.type.format(date!!)

        return formatted
    }


    sealed class Format(val type: SimpleDateFormat) {
        data object WeekDayMonthFormat: Format(SimpleDateFormat("EEE, dd MMM", locale))
        data object DayMonthYearFormat: Format(SimpleDateFormat("dd.MM.yyyy", locale))
    }
}