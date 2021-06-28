package com.flyingjetski.budgeteer.models

import com.flyingjetski.budgeteer.models.enums.CategoryType

open class Category(
    icon  : String,
    label : String,
    type  : CategoryType,
) {
    val icon  = icon
    val label = label
    val type  = type
}