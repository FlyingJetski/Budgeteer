package com.flyingjetski.budgeteer.models

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.models.enums.Currency
import com.flyingjetski.budgeteer.models.enums.Feedback
import com.flyingjetski.budgeteer.models.enums.FrequencyType
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import java.util.*

class Income(
    uid         : String?,
    date        : Date,
    sourceId    : String,
    source      : Source?,
    currency    : Currency,
    categoryId  : String,
    category    : IncomeCategory?,
    label       : String,
    amount      : Double,
    details     : String?,
) {
    var id: String? = null
    val uid         = uid
    val date        = date
    val sourceId    = sourceId
    val source      = source
    val currency    = currency
    val categoryId  = categoryId
    val category    = category
    val label       = label
    val amount      = amount
    val details     = details

    constructor(): this(null, Date(), "", Source(), Currency.MYR, "", IncomeCategory(), "", 0.0, "")

    companion object {
        fun insertIncome(income: Income) {
            AuthActivity().db.collection("Incomes").add(income)
        }

        fun updateIncomeById(
            id         : String,
            date       : Date?,
            sourceId   : String?,
            currency   : Currency?,
            categoryId : String?,
            label      : String?,
            amount     : Double?,
            details    : String?,
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
            AuthActivity().db.collection("Incomes")
                .document(id).update(data)
        }

        fun deleteIncomeById(id: String) {
            AuthActivity().db.collection("Incomes")
                .document(id).delete()
        }

        fun getIncomeById(id: String): Task<DocumentSnapshot> {
            return AuthActivity().db.collection("Incomes")
                .document(id).get()
        }

        fun getIncome(): Query {
            return AuthActivity().db.collection("Incomes")
                .whereEqualTo("uid", AuthActivity().auth.uid.toString())
        }
    }


}