package com.flyingjetski.budgeteer.models

import java.util.*

class Saving(
    isActive: Boolean,
    icon: String,
    label: String,
    currency: Currency,
    target: Double,
    deadline: Date,
    autoSave: AutoSave
): Source(icon, label, currency) {
}