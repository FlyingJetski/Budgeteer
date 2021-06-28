package com.flyingjetski.budgeteer.models

import java.util.*

class Budget(
    isActive: Boolean,
    icon: String,
    label: String,
    currency: Currency,
    amount: Double,
    startDate: Date,
    duration: Date,
    isRecurring: Boolean
): Source(icon, label, currency) {

}