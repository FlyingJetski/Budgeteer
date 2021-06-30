package com.flyingjetski.budgeteer.models

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.models.enums.Currency
import com.flyingjetski.budgeteer.models.enums.Feedback
import java.util.*

class Expense(
    id         : String?,
    date       : Date,
    source     : Source,
    currency   : Currency,
    categoryId : String,
    category   : ExpenseCategory,
    label      : String,
    amount     : Double,
    details    : String,
    feedback   : Feedback,
) {
    val id         = id
    val date       = date
    val source     = source
    val currency   = currency
    val categoryId = categoryId
    val category   = category
    val label      = label
    val amount     = amount
    val details    = details
    val feedback   = feedback

    constructor(): this(null, Date(), Source(), Currency.MYR, "", ExpenseCategory(), "", 0.0, "", Feedback.NEUTRAL)

    companion object {
        fun insertExpense(fragment: Fragment, expense: Expense) {
            AuthActivity().db.collection("Expenses").add(expense)
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

        fun getExpenseById(id: String) {
            var expense = Expense()
            AuthActivity().db.collection("Expenses")
                .document(id).set(expense)
        }
    }

}