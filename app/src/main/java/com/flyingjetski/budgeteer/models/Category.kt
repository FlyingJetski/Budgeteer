package com.flyingjetski.budgeteer.models

import com.flyingjetski.budgeteer.models.enums.CategoryType

open class Category(
    uid   : String?,
    icon  : Int,
    label : String,
    type  : CategoryType,
) {
    var id: String? = null
    var uid   = uid
    var icon  = icon
    var label = label
    var type  = type
}