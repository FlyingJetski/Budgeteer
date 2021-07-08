package com.flyingjetski.budgeteer.models

import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.Callback
import com.flyingjetski.budgeteer.models.enums.Currency
import com.flyingjetski.budgeteer.models.enums.Feedback
import com.flyingjetski.budgeteer.models.enums.SourceType
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import java.util.*

class Expense(
    uid        : String?,
    date       : Date,
    sourceId   : String,
    source     : Source?,
    currency   : Currency,
    categoryId : String,
    category   : ExpenseCategory?,
    label      : String,
    amount     : Double,
    details    : String?,
    feedback   : Feedback,
) {
    var id: String? = null
    val uid        = uid
    val date       = date
    val sourceId   = sourceId
    val source     = source
    val currency   = currency
    val categoryId = categoryId
    val category   = category
    val label      = label
    val amount     = amount
    val details    = details
    val feedback   = feedback

    constructor(): this(null, Date(), "", Source(), Currency.MYR, "", ExpenseCategory(), "", 0.0, "", Feedback.NEUTRAL)

    companion object {
        fun insertExpense(expense: Expense) {
            AuthActivity().db.collection("Expenses").add(expense)
        }

        fun updateExpenseById(
            id         : String,
            date       : Date?,
            sourceId   : String?,
            currency   : Currency?,
            categoryId : String?,
            label      : String?,
            amount     : Double?,
            details    : String?,
            feedback   : Feedback?,

        ) {
            val data = HashMap<String, Any>()
            if (date != null) {
                data["date"] = date
            }
            if (sourceId != null && sourceId != "") {
                data["sourceId"] = sourceId
            }
            if (currency != null) {
                data["currency"] = currency
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
            AuthActivity().db.collection("Expenses")
                .document(id).delete()
        }

        fun getExpenseById(id: String, callback: Callback) {
            AuthActivity().db.collection("Expenses")
                .document(id).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        var expense = document.toObject(Expense::class.java)!!
                        if (expense != null) {
                            callback.onCallback(expense)
                        }
                    }
                }
        }

        fun getExpense(callback: Callback) {
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