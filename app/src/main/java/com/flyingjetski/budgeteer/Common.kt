package com.flyingjetski.budgeteer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import java.text.SimpleDateFormat
import java.util.*


class Common {
    companion object {
        const val expenseCategoryIconCount = 30
        val expenseCategoryIcons: ArrayList<Int> = ArrayList()
        const val incomeCategoryIconCount = 8
        val incomeCategoryIcons: ArrayList<Int> = ArrayList()
        const val sourceIconCount = 6
        val sourceIcons: ArrayList<Int> = ArrayList()

        // rounds a double to 2 decimal points
        fun roundDouble(double: Double): Double {
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