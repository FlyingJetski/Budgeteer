package com.flyingjetski.budgeteer.models

import com.flyingjetski.budgeteer.models.enums.Feedback
import java.util.*

class Expense(
    source: Source,
    currency: Currency,
    category: ExpenseCategory,
    label: String,
    amount: Double,
    details: String,
    feedback: Feedback
) {

}