package com.flyingjetski.budgeteer.models

import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.Callback
import com.flyingjetski.budgeteer.models.enums.Currency
import com.flyingjetski.budgeteer.models.enums.SourceType
import java.util.*

class Budget(
    uid         : String?,
    isActive    : Boolean,
    icon        : Int,
    label       : String,
    currency    : Currency,
    amount      : Double,
    startDate   : Date,
    endDate     : Date,
    isRecurring : Boolean,
): Source(uid, icon, label, SourceType.BUDGET, currency) {
    val isActive    = isActive
    val amount      = amount
    val startDate   = startDate
    val endDate     = endDate
    val isRecurring = isRecurring

    constructor(): this(null, false, 0, "", Currency.MYR, 0.0, Date(), Date(), false)

    companion object {
        fun insertBudget(source: Budget) {
            AuthActivity().db.collection("Sources").add(source)
        }

        fun updateBudgetById(
            id: String,
            isActive: Boolean?,
            icon: Int?,
            label: String?,
            currency: Currency?,
            amount: Double?,
            startDate: Date?,
            endDate: Date?,
            isRecurring: Boolean?,
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
            if (endDate != null) {
                data["endDate"] = endDate
            }
            if (isRecurring != null) {
                data["isRecurring"] = isRecurring
            }

            AuthActivity().db.collection("Sources")
                .document(id).update(data)
        }

        fun getBudgetById(id: String, callback: Callback) {
            AuthActivity().db.collection("Sources")
                .document(id).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        var budget = document.toObject(Budget::class.java)!!
                        if (budget != null) {
                            callback.onCallback(budget)
                        }
                    }
                }
        }

        fun getBudget(callback: Callback) {
            AuthActivity().db.collection("Sources")
                .whereEqualTo("uid", AuthActivity().auth.uid.toString())
                .whereEqualTo("type", SourceType.BUDGET)
                .addSnapshotListener { snapshot, _ ->
                    run {
                        if (snapshot != null) {
                            val budgets = ArrayList<Budget>()
                            val documents = snapshot.documents
                            documents.forEach {
                                val budget = it.toObject(Budget::class.java)
                                if (budget != null) {
                                    budget.id = it.id
                                    budgets.add(budget)
                                }
                            }
                            callback.onCallback(budgets)
                        }
                    }
                }
        }
    }
}