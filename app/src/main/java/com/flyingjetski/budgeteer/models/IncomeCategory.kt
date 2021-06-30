package com.flyingjetski.budgeteer.models

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.models.enums.CategoryType

class IncomeCategory(
    id    : String?,
    icon  : String,
    label : String,
): Category(icon, label, CategoryType.INCOME) {

    constructor(): this(null, "", "")

    companion object {
        fun insertIncomeCategory(fragment: Fragment, category: IncomeCategory) {
            AuthActivity().db.collection("Categories").add(category)
                .addOnCompleteListener(fragment.requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(fragment.context, "Added successfully.",
                            Toast.LENGTH_SHORT).show()
                        fragment.requireActivity().finish()
                    } else {
                        Toast.makeText(fragment.context, "Addition failed, please try again.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

        fun getIncomeCategoryById(id: String) {
            var category = IncomeCategory()
            AuthActivity().db.collection("Categories")
                .document(AuthActivity().auth.uid.toString()).set(category)
        }
    }


}