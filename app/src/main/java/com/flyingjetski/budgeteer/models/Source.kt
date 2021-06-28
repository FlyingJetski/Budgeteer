package com.flyingjetski.budgeteer.models

import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.models.enums.Currency
import com.flyingjetski.budgeteer.models.enums.SourceType

open class Source(
    icon     : String,
    label    : String,
    source   : SourceType,
    currency : Currency,
) {
    val icon     = icon
    val label    = label
    val source   = source
    val currency = currency

    constructor(): this("", "", SourceType.WALLET, Currency.MYR)

}