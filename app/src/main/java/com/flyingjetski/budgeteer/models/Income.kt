package com.flyingjetski.budgeteer.models

import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.Callback
import com.flyingjetski.budgeteer.models.enums.Currency
import com.google.firebase.firestore.Query
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Income(
    uid         : String?,
    date        : Date,
    sourceId    : String,
    source      : Source?,
    categoryId  : String,
    category    : IncomeCategory?,
    label       : String,
    amount      : Double,
    details     : String?,
) {
    var id: String? = null
    var currency: Currency? = null
    val uid         = uid
    val date        = date
    val sourceId    = sourceId
    val source      = source
    val categoryId  = categoryId
    val category    = category
    val label       = label
    val amount      = amount
    val details     = details

    constructor(): this(null, Date(), "", Source(), "", IncomeCategory(), "", 0.0, "")

    companion object {
        fun insertIncome(income: Income) {
            Source.getSourceById(income.sourceId, object: Callback {
                override fun onCallback(value: Any) {
                    val source = value as Source
                    income.currency = source.currency
                    AuthActivity().db.collection("Incomes").add(income)
                    Source.updateSourceAmountById(income.sourceId, income.amount)
                }
            })
        }

        fun updateIncomeById(
            id         : String,
            date       : Date?,
            sourceId   : String?,
            categoryId : String?,
            label      : String?,
            amount     : Double?,
            details    : String?,
            ) {
            getIncomeById(id, object : Callback {
                override fun onCallback(value: Any) {
                    val income = value as Income
                    if (amount != null) {
                        Source.updateSourceAmountById(income.sourceId, -(income.amount - amount))
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
            AuthActivity().db.collection("Incomes")
                .document(id).update(data)
        }

        fun deleteIncomeById(id: String) {
            getIncomeById(id, object : Callback {
                override fun onCallback(value: Any) {
                    val income = value as Income
                    Source.updateSourceAmountById(income.sourceId, -income.amount)
                }
            })
            AuthActivity().db.collection("Incomes")
                .document(id).delete()
        }

        fun deleteIncomeByCategoryId(id: String) {
            AuthActivity().db.collection("Incomes")
                .whereEqualTo("uid", AuthActivity().auth.uid.toString())
                .whereEqualTo("categoryId", id)
                .get().addOnSuccessListener { query ->
                    query.documents.forEach { document ->
                        if (document != null) {
                            val income = document.toObject(Income::class.java)!!
                            Source.updateSourceAmountById(income.sourceId, -income.amount)
                            document.reference.delete()
                        }
                    }
                }
        }

        fun deleteIncomeBySourceId(id: String) {
            AuthActivity().db.collection("Incomes")
                .whereEqualTo("uid", AuthActivity().auth.uid.toString())
                .whereEqualTo("sourceId", id)
                .get().addOnSuccessListener { query ->
                    query.documents.forEach { document ->
                        if (document != null) {
                            val income = document.toObject(Income::class.java)!!
                            Budget.updateBudgetAmountSpent(income.currency!!, income.date, income.amount, null, null)
                            document.reference.delete()
                        }
                    }
                }
        }

        fun getIncomeById(id: String, callback: Callback) {
            AuthActivity().db.collection("Incomes")
                .document(id).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val income = document.toObject(Income::class.java)!!
                        callback.onCallback(income)
                    }
                }
        }

        fun getIncome(currency: Currency?, sourceId: String?, dateStart: Date, dateEnd: Date, callback: Callback) {
            var query = AuthActivity().db.collection("Incomes")
                .whereEqualTo("uid", AuthActivity().auth.uid.toString())
            if (sourceId != null) {
                query = query
                    .whereEqualTo("sourceId", sourceId)
            }
            if (currency != null) {
                query = query
                    .whereEqualTo("currency", currency)
            }
            query
                .whereGreaterThanOrEqualTo("date", dateStart)
                .whereLessThan("date", dateEnd)
                .orderBy("date", Query.Direction.DESCENDING)
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

        fun updateIncomeCurrencyBySourceId(id: String, currency: Currency) {
            AuthActivity().db.collection("Incomes")
                .whereEqualTo("uid", AuthActivity().auth.uid.toString())
                .whereEqualTo("sourceId", id)
                .get().addOnSuccessListener { query ->
                    query.documents.forEach { document ->
                        document.reference.update("currency", currency)
                    }
                }
        }

        fun getAllIncome(callback: Callback) {
            AuthActivity().db.collection("Incomes")
                .whereEqualTo("uid", AuthActivity().auth.uid.toString())
                .orderBy("date", Query.Direction.DESCENDING)
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