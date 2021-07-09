package com.flyingjetski.budgeteer

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class Common {
    companion object {
        fun roundDouble(double: Double): Double {
            // rounds a double to 2 decimal points
            return "%.2f".format(double).toDouble()
        }

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