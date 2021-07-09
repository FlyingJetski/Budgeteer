package com.flyingjetski.budgeteer.models

import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.Callback
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

    constructor(): this(null, 0, "", CategoryType.EXPENSE)

    companion object {
        fun updateCategoryById(id: String, icon: Int?, label: String?) {
            val data = HashMap<String, Any>()
            if (icon != null && icon != 0) {
                data["icon"] = icon
            }
            if (label != null && label != "") {
                data["label"] = label
            }
            AuthActivity().db.collection("Categories")
                .document(id).update(data)
        }

        fun deleteCategoryById(id: String) {
            AuthActivity().db.collection("Categories")
                .document(id).delete()
        }

        fun getCategoryById(id: String, callback: Callback) {
            AuthActivity().db.collection("Categories")
                .document(id).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val category = document.toObject(Category::class.java)!!
                        callback.onCallback(category)
                    }
                }
        }
    }
}