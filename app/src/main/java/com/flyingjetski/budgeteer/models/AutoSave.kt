package com.flyingjetski.budgeteer.models

import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.models.enums.FrequencyType
import java.util.*

class AutoSave(
    startDate     : Date,
    amount        : Double,
    frequency     : Int,
    frequencyType : FrequencyType,
) {
    val startDate     = startDate
    val amount        = amount
    val frequency     = frequency
    val frequencyType = frequencyType
}