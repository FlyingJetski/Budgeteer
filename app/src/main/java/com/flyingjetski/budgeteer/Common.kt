package com.flyingjetski.budgeteer

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class Common {
    companion object {
        fun dateToString(date: Date): String {
            val datePattern = "dd/MM/yy"
            val sdf = SimpleDateFormat(datePattern, Locale.US)
            return sdf.format(date)
        }

        fun stringToDate(string: String): Date {
            val datePattern = "dd/MM/yy"
            val format = SimpleDateFormat(datePattern)
            return format.parse(string)
        }
    }
}