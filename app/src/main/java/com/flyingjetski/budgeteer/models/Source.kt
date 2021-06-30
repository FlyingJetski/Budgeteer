package com.flyingjetski.budgeteer.models

import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.models.enums.Currency
import com.flyingjetski.budgeteer.models.enums.SourceType

open class Source(
    id       : String?,
    icon     : String,
    label    : String,
    source   : SourceType,
    currency : Currency,
) {
    val id       = id
    val icon     = icon
    val label    = label
    val source   = source
    val currency = currency

    constructor(): this(null, "", "", SourceType.WALLET, Currency.MYR)

}