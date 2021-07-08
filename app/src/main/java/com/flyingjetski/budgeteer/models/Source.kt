package com.flyingjetski.budgeteer.models

import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.Callback
import com.flyingjetski.budgeteer.models.enums.Currency
import com.flyingjetski.budgeteer.models.enums.SourceType

open class Source(
    uid      : String?,
    icon     : Int,
    label    : String,
    type     : SourceType,
    currency : Currency,
) {
    var id: String? = null
    val uid      = uid
    val icon     = icon
    val label    = label
    val type     = type
    val currency = currency

    constructor(): this(null, 0, "", SourceType.WALLET, Currency.MYR)

    companion object {
        fun deleteSourceById(id: String) {
            AuthActivity().db.collection("Sources")
                .document(id).delete()
        }

        fun getSourceById(id: String, callback: Callback) {
            AuthActivity().db.collection("Sources")
                .document(id).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        var source = document.toObject(Source::class.java)!!
                        if (source != null) {
                            callback.onCallback(source)
                        }
                    }
                }
        }

        fun getSource(callback: Callback) {
            AuthActivity().db.collection("Sources")
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