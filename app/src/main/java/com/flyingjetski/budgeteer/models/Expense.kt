package com.flyingjetski.budgeteer.models

import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.Callback
import com.flyingjetski.budgeteer.models.enums.Currency
import com.flyingjetski.budgeteer.models.enums.Feedback
import java.util.*

class Expense(
    uid        : String?,
    date       : Date,
    sourceId   : String,
    source     : Source?,
    categoryId : String,
    category   : ExpenseCategory?,
    label      : String,
    amount     : Double,
    details    : String?,
    feedback   : Feedback,
) {
    var id: String? = null
    var currency: Currency? = null
    val uid        = uid
    val date       = date
    val sourceId   = sourceId
    val source     = source
    val categoryId = categoryId
    val category   = category
    val label      = label
    val amount     = amount
    val details    = details
    val feedback   = feedback

    constructor(): this(null, Date(), "", Source(), "", ExpenseCategory(), "", 0.0, "", Feedback.NEUTRAL)

    companion object {
        fun insertExpense(expense: Expense) {
            Source.getSourceById(expense.categoryId, object: Callback {
                override fun onCallback(value: Any) {
                    val source = value as Source
                    expense.currency = source.currency
                }
            })
            AuthActivity().db.collection("Expenses").add(expense)
            Source.updateSourceAmountById(expense.sourceId, -expense.amount)
            Budget.updateBudgetAmountSpent(expense.currency!!, null, null, expense.date, -expense.amount)
        }

        fun updateExpenseById(
            id         : String,
            date       : Date?,
            sourceId   : String?,
            categoryId : String?,
            label      : String?,
            amount     : Double?,
            details    : String?,
            feedback   : Feedback?,
        ) {
            getExpenseById(id, object: Callback {
                override fun onCallback(value: Any) {
                    val expense = value as Expense
                    if (date != null && amount != null) {
                        Budget.updateBudgetAmountSpent(expense.currency!!, expense.date, expense.amount, date, -amount)
                        return
                    }
                    if (amount != null) {
                        Source.updateSourceAmountById(expense.sourceId, expense.amount - amount)
                        Budget.updateBudgetAmountSpent(expense.currency!!, expense.date, expense.amount, expense.date, -amount)
                        return
                    }
                    if (date != null) {
                        Budget.updateBudgetAmountSpent(expense.currency!!, expense.date, expense.amount, date, -expense.amount)
                        return
                    }
                }
            })

            val data = HashMap<String, Any>()
            if (date != null) {
                data["date"] = date
            }
            if (sourceId != null && sourceId != "") {
                Source.getSourceById(sourceId, object: Callback {
                    override fun onCallback(value: Any) {
                        val source = value as Source
                        data["currency"] = source.currency
                    }
                })
                data["sourceId"] = sourceId
            }
            if (categoryId != null && categoryId != "") {
                data["categoryId"] = categoryId
            }
            if (label != null && label != "") {
                data["label"] = label
            }
            if (amount != null && amount != 0.0) {
                data["amount"] = amount
            }
            if (details != null && details != "") {
                data["details"] = details
            }
            if (feedback != null) {
                data["feedback"] = feedback
            }
            AuthActivity().db.collection("Expenses")
                .document(id).update(data)
        }

        fun deleteExpenseById(id: String) {
            getExpenseById(id, object: Callback {
                override fun onCallback(value: Any) {
                    val expense = value as Expense
                    Source.updateSourceAmountById(expense.sourceId, expense.amount)
                    Budget.updateBudgetAmountSpent(expense.currency!!, expense.date, expense.amount, null, null)
                }
            })

            AuthActivity().db.collection("Expenses")
                .document(id).delete()
        }

        fun deleteExpenseBySourceId(id: String) {
            AuthActivity().db.collection("Expenses")
                .whereEqualTo("uid", AuthActivity().auth.uid.toString())
                .whereEqualTo("sourceId", id)
                .get().addOnSuccessListener { query ->
                    query.documents.forEach { document ->
                        document.reference.delete()
                    }
                }
        }

        fun getExpenseById(id: String, callback: Callback) {
            AuthActivity().db.collection("Expenses")
                .document(id).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val expense = document.toObject(Expense::class.java)!!
                        callback.onCallback(expense)
                    }
                }
        }

        fun getExpense(sourceId: String?, dateStart: Date, dateEnd: Date, callback: Callback) {
            var query = AuthActivity().db.collection("Expenses")
                .whereEqualTo("uid", AuthActivity().auth.uid.toString())
            if (sourceId != null) {
                query = query
                    .whereEqualTo("sourceId", sourceId)
            }
            query
                .whereGreaterThanOrEqualTo("date", dateStart)
                .whereLessThan("date", dateEnd)
                .addSnapshotListener { snapshot, _ ->
                    run {
                        if (snapshot != null) {
                            val expenses = ArrayList<Expense>()
                            val documents = snapshot.documents
                            documents.forEach {
                                val expense = it.toObject(Expense::class.java)
                                if (expense != null) {
                                    expense.id = it.id
                                    expenses.add(expense)
                                }
                            }
                            callback.onCallback(expenses)
                        }
                    }
                }
        }

        fun updateExpenseCurrencyBySourceId(id: String, currency: Currency) {
            AuthActivity().db.collection("Expenses")
                .whereEqualTo("uid", AuthActivity().auth.uid.toString())
                .whereEqualTo("sourceId", id)
                .get().addOnSuccessListener { query ->
                    query.documents.forEach { document ->
                        document.reference.update("currency", currency)
                    }
                }
        }

        fun getAllExpense(callback: Callback) {
            AuthActivity().db.collection("Expenses")
                .whereEqualTo("uid", AuthActivity().auth.uid.toString())
                .addSnapshotListener { snapshot, _ ->
                    run {
                        if (snapshot != null) {
                            val expenses = ArrayList<Expense>()
                            val documents = snapshot.documents
                            documents.forEach {
                                val expense = it.toObject(Expense::class.java)
                                if (expense != null) {
                                    expense.id = it.id
                                    expenses.add(expense)
                                }
                            }
                            callback.onCallback(expenses)
                        }
                    }
                }
        }
    }

}