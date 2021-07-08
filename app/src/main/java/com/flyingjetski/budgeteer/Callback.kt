package com.flyingjetski.budgeteer

import com.flyingjetski.budgeteer.models.Category

interface Callback {
//    fun onCategoryCallback(value: Category)
    fun onCallback(value: Any)
}