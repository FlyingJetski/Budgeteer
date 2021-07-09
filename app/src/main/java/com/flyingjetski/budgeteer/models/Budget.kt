package com.flyingjetski.budgeteer.models

import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.Callback
import com.flyingjetski.budgeteer.models.enums.Currency
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.QuerySnapshot
import java.util.*

class Budget(
    uid         : String?,
    isActive    : Boolean,
    icon        : Int,
    label       : String,
    amount      : Double,
    currency    : Currency,
    startDate   : Date,
    endDate     : Date,
    isRecurring : Boolean,
) {
    var id: String? = null
    val amountSpent: Double = 0.0
    val uid         = uid
    val isActive    = isActive
    val icon        = icon
    val label       = label
    val amount      = amount
    val currency    = currency
    val startDate   = startDate
    val endDate     = endDate
    val isRecurring = isRecurring

    constructor(): this(null, false, 0, "", 0.0, Currency.MYR, Date(), Date(), false)

    companion object {
        fun insertBudget(budget: Budget) {
            AuthActivity().db.collection("Budgets").add(budget).addOnSuccessListener {
                initializeBudgetAmountSpentById(it.id, budget.startDate, budget.endDate)
            }
        }

        fun updateBudgetById(
            id: String,
            isActive: Boolean?,
            icon: Int?,
            label: String?,
            amount: Double?,
            currency: Currency?,
            startDate: Date?,
            endDate: Date?,
            isRecurring: Boolean?,
        ) {
            if (startDate != null || endDate != null) {
                resetBudgetAmountSpentById(id)
                initializeBudgetAmountSpentById(id, startDate, endDate)
            }

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
            if (amount != null && amount != 0.0) {
                data["amount"] = amount
            }
            if (currency != null) {
                data["currency"] = currency
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

            AuthActivity().db.collection("Budgets")
                .document(id).update(data)
        }

        private fun initializeBudgetAmountSpentById(id: String, startDate: Date?, endDate: Date?) {
            getBudgetById(id, object: Callback {
                override fun onCallback(value: Any) {
                    val budget = value as Budget
                    var query = AuthActivity().db.collection("Expenses")
                        .whereEqualTo("uid", AuthActivity().auth.uid.toString())
                    query = if (startDate != null) {
                        query.whereGreaterThanOrEqualTo("date", startDate)
                    } else {
                        query.whereGreaterThanOrEqualTo("date", budget.startDate)
                    }
                    query = if (endDate != null) {
                        query.whereLessThanOrEqualTo("date", endDate)
                    } else {

                        query.whereGreaterThanOrEqualTo("date", budget.endDate)
                    }
                    query
                        .get().addOnSuccessListener { querySnapshot: QuerySnapshot ->
                            querySnapshot.documents.forEach { document ->
                                val expense = document.toObject(Expense::class.java)
                                if (budget.currency == expense?.currency) {
                                    updateBudgetAmountSpentById(id, expense.amount)
                                }
                            }
                        }
                }
            })
        }

        private fun updateBudgetAmountSpentById(id: String, amount: Double) {
            AuthActivity().db.collection("Budgets")
                .document(id).update("amountSpent", FieldValue.increment(amount))
        }

        private fun resetBudgetAmountSpentById(id: String) {
            getBudgetById(id, object: Callback {
                override fun onCallback(value: Any) {
                    val budget = value as Budget
                    AuthActivity().db.collection("Budgets")
                        .document(id).update("amountSpent", budget.amount)
                }
            })
        }

        fun updateBudgetAmountSpent(currency: Currency, oldDate: Date?, oldAmount:Double?, date: Date?, amount: Double?) {
            // Update budgets based on expense's old date
            if (oldDate != null || oldAmount != null) {
                AuthActivity().db.collection("Budgets")
                    .whereEqualTo("uid", AuthActivity().auth.uid.toString())
                    .whereEqualTo("currency", currency)
                    .whereGreaterThanOrEqualTo("startDate", oldDate!!)
                    .whereLessThanOrEqualTo("endDate", oldDate)
                    .get().addOnSuccessListener { query ->
                        query.documents.forEach { document ->
                            document.reference.update("amountSpent", FieldValue.increment(oldAmount!!))
                        }
                    }
            }

            // Update budgets based on expense's current date
            if (date != null || amount != null) {
                AuthActivity().db.collection("Budgets")
                    .whereEqualTo("uid", AuthActivity().auth.uid.toString())
                    .whereEqualTo("currency", currency)
                    .whereGreaterThanOrEqualTo("startDate", date!!)
                    .whereLessThanOrEqualTo("endDate", date)
                    .get().addOnSuccessListener { query ->
                        query.documents.forEach { document ->
                            document.reference.update("amountSpent", FieldValue.increment(amount!!))
                        }
                    }
            }
        }

        fun getBudgetById(id: String, callback: Callback) {
            AuthActivity().db.collection("Budgets")
                .document(id).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val budget = document.toObject(Budget::class.java)!!
                        callback.onCallback(budget)
                    }
                }
        }

        fun getBudget(callback: Callback) {
            AuthActivity().db.collection("Budgets")
                .whereEqualTo("uid", AuthActivity().auth.uid.toString())
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