package com.flyingjetski.budgeteer.models

import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.Callback
import com.flyingjetski.budgeteer.models.enums.Currency
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

        fun getIncomeById(id: String, callback: Callback) {
            AuthActivity().db.collection("Incomes")
                .document(id).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        var income = document.toObject(Income::class.java)!!
                        if (income != null) {
                            callback.onCallback(income)
                        }
                    }
                }
        }

        fun getIncome(sourceId: String?, dateStart: Date, dateEnd: Date, callback: Callback) {
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
                            val incomes = ArrayList<Income>()
                            val documents = snapshot.documents
                            documents.forEach {
                                val income = it.toObject(Income::class.java)
                                if (income != null) {
                                    income.id = it.id
                                    incomes.add(income)
                                }
                            }
                            callback.onCallback(incomes)
                        }
                    }
                }
        }

        fun getAllIncome(callback: Callback) {
            AuthActivity().db.collection("Incomes")
                .whereEqualTo("uid", AuthActivity().auth.uid.toString())
                .addSnapshotListener { snapshot, _ ->
                    run {
                        if (snapshot != null) {
                            val incomes = ArrayList<Income>()
                            val documents = snapshot.documents
                            documents.forEach {
                                val income = it.toObject(Income::class.java)
                                if (income != null) {
                                    income.id = it.id
                                    incomes.add(income)
                                }
                            }
                            callback.onCallback(incomes)
                        }
                    }
                }
        }
    }


}