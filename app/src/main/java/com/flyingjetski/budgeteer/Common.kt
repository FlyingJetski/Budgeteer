package com.flyingjetski.budgeteer

import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*

class Common {
    companion object {
        fun updateLabel(calendar: Calendar, editText: EditText) {
            val myFormat = "dd/MM/yy" //In which you need put here
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            editText.setText(sdf.format(calendar.time))
        }
    }
}