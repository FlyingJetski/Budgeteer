package com.flyingjetski.budgeteer.models

import com.flyingjetski.budgeteer.models.enums.CategoryType

open class Category(
    id    : String?,
    icon  : String,
    label : String,
    type  : CategoryType,
) {
    val id    = id
    val icon  = icon
    val label = label
    val type  = type
}