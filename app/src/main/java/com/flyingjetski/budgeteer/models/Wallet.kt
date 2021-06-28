package com.flyingjetski.budgeteer.models

import java.util.*

class Wallet(
    icon: String,
    label: String,
    currency: Currency,
    isMain: Boolean
): Source(icon, label, currency) {
}