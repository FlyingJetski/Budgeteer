package com.flyingjetski.budgeteer.models

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.models.enums.Currency
import com.flyingjetski.budgeteer.models.enums.FrequencyType
import java.util.*

class Income(
    id          : String?,
    date        : Date,
    destination : Source,
    currency    : Currency,
    category    : IncomeCategory,
    label       : String,
    amount      : Double,
    details     : String,
) {
    val id          = id
    val date        = date
    val destination = destination
    val currency    = currency
    val category    = category
    val label       = label
    val amount      = amount
    val details     = details

    constructor(): this(null, Date(), Source(), Currency.MYR, IncomeCategory(), "", 0.0, "")

    companion object {
        fun insertIncome(fragment: Fragment, income: Income) {
            AuthActivity().db.collection("Incomes").add(income)
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

        fun getIncomeById(id: String) {
            var income = Income()
            AuthActivity().db.collection("Incomes")
                .document(id).set(income)
        }
    }


}