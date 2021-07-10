package com.flyingjetski.budgeteer.models

import android.util.Log
import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.Callback
import com.flyingjetski.budgeteer.MainActivity
import com.flyingjetski.budgeteer.models.enums.Currency
import com.flyingjetski.budgeteer.models.enums.SourceType
import com.google.firebase.firestore.FieldValue
import java.util.HashMap

open class Source(
    uid      : String?,
    icon     : Int,
    label    : String,
    type     : SourceType,
    currency : Currency,
) {
    var id: String? = null
    val amount: Double? = null
    val uid      = uid
    val icon     = icon
    val label    = label
    val type     = type
    val currency = currency

    constructor(): this(null, 0, "", SourceType.WALLET, Currency.MYR)

    companion object {
        fun updateSourceAmountById(id: String, amount: Double) {
            MainActivity().db.collection("Sources")
                .document(id).update("amount", FieldValue.increment(amount))
        }

        fun deleteSourceById(id: String) {
            Expense.deleteExpenseBySourceId(id)
            Income.deleteIncomeBySourceId(id)
            MainActivity().db.collection("Sources")
                .document(id).delete()
        }

        fun getSourceById(id: String, callback: Callback) {
            MainActivity().db.collection("Sources")
                .document(id).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val source = document.toObject(Source::class.java)!!
                        callback.onCallback(source)
                    }
                }
        }

        fun getSource(callback: Callback) {
            MainActivity().db.collection("Sources")
                .whereEqualTo("uid", AuthActivity().auth.uid.toString())
                .addSnapshotListener{
                        snapshot, _ ->
                    run {
                        if (snapshot != null) {
                            val sources = ArrayList<Source>()
                            val documents = snapshot.documents
                            documents.forEach {
                                val source = it.toObject(Wallet::class.java)
                                if (source != null) {
                                    source.id = it.id
                                    sources.add(source)
                                }
                            }
                            callback.onCallback(sources)
                        }
                    }
                }
        }
    }

}