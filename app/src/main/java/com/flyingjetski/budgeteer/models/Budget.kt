package com.flyingjetski.budgeteer.models

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.models.enums.Currency
import com.flyingjetski.budgeteer.models.enums.FrequencyType
import com.flyingjetski.budgeteer.models.enums.SourceType
import java.util.*

class Budget(
    isActive    : Boolean,
    icon        : String,
    label       : String,
    currency    : Currency,
    amount      : Double,
    startDate   : Date,
    duration    : Date,
    isRecurring : Boolean,
): Source(null, icon, label, SourceType.BUDGET, currency) {
    val isActive    = isActive
    val amount      = amount
    val startDate   = startDate
    val duration    = duration
    val isRecurring = isRecurring

    constructor(): this(false, "", "", Currency.MYR, 0.0, Date(), Date(), false)

    companion object {
        fun insertBudget(fragment: Fragment, source: Budget) {
            AuthActivity().db.collection("Sources").add(source)
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

        fun getBudgetById(id: String) {
            var source = Budget()
            AuthActivity().db.collection("Sources")
                .document(id).set(source)
        }
    }

}