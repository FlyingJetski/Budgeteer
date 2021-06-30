package com.flyingjetski.budgeteer.models

import android.R
import android.util.Log
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.models.enums.CategoryType
import com.google.firebase.firestore.ktx.toObject

class ExpenseCategory(
    id    : String?,
    icon  : String,
    label : String,
): Category(null, icon, label, CategoryType.EXPENSE) {

    constructor(): this(null, "", "")

    companion object {
        fun insertExpenseCategory(fragment: Fragment, category: ExpenseCategory) {
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

        fun getExpenseCategoryById(fragment: Fragment, id: String) {
            var category = ExpenseCategory()
            AuthActivity().db.collection("Categories")
                .document(AuthActivity().auth.uid.toString()).set(category)
        }

        fun getExpenseCategory(fragment: Fragment, listView: ListView): List<ExpenseCategory> {
            var categories = mutableListOf<ExpenseCategory>()
            val result = AuthActivity().db.collection("Categories").get()
                .addOnCompleteListener{
                    if (it.isSuccessful) {
                        val documentSnapshot = it.result
                        if (documentSnapshot?.isEmpty == false) {
                            categories = documentSnapshot.toObjects(ExpenseCategory::class.java)
                            listView.adapter = ArrayAdapter(
                                fragment.requireContext(),
                                R.layout.simple_list_item_1,
                                categories
                            )
                        }
                    } else {
                        Toast.makeText(fragment.context, "Failed retrieving data.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            return categories
        }

        override fun toString(): String {
            return super.toString()
        }
    }
}