package com.flyingjetski.budgeteer.models

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.models.enums.Currency
import com.flyingjetski.budgeteer.models.enums.FrequencyType
import com.flyingjetski.budgeteer.models.enums.SourceType
import com.google.firebase.firestore.Query
import java.util.*

class Budget(
    uid         : String?,
    isActive    : Boolean,
    icon        : Int,
    label       : String,
    currency    : Currency,
    amount      : Double,
    startDate   : Date,
    duration    : Date,
    isRecurring : Boolean,
): Source(uid, icon, label, SourceType.BUDGET, currency) {
    val isActive    = isActive
    val amount      = amount
    val startDate   = startDate
    val duration    = duration
    val isRecurring = isRecurring

    constructor(): this(null, false, 0, "", Currency.MYR, 0.0, Date(), Date(), false)

    companion object {
        fun insertBudget(source: Saving) {
            AuthActivity().db.collection("Sources").add(source)
        }

        fun updateBudgetById(
            id          : String,
            isActive    : Boolean?,
            icon        : Int?,
            label       : String?,
            currency    : Currency?,
            amount      : Double?,
            startDate   : Date?,
            duration    : Date?,
            isRecurring : Boolean?,
        ) {
            val data = HashMap<String, Any>()
            if (isActive != null) {
                data["isActive"] = isActive
            }
            if (icon != null && icon != 0) {
                data["icon"] = icon
            }
            if (label != null && label != "") {
                data["label"] = label
            }
            if (currency != null) {
                data["currency"] = currency
            }
            if (amount != null && amount != 0.0) {
                data["amount"] = amount
            }
            if (startDate != null) {
                data["startDate"] = startDate
            }
            if (duration != null) {
                data["duration"] = duration
            }
            if (isRecurring != null) {
                data["isRecurring"] = isRecurring
            }

            AuthActivity().db.collection("Sources")
                .document(id).update(data)
        }

        fun getBudget(): Query {
            return AuthActivity().db.collection("Sources")
                .whereEqualTo("uid", AuthActivity().auth.uid.toString())
                .whereEqualTo("type", SourceType.BUDGET)
        }
    }

}