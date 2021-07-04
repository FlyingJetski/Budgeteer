package com.flyingjetski.budgeteer.models

import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.models.enums.Currency
import com.flyingjetski.budgeteer.models.enums.SourceType
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import java.util.HashMap

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
        fun getSourceById(id: String): Task<DocumentSnapshot> {
            return AuthActivity().db.collection("Sources")
                .document(id).get()
        }

        fun getSource(): Query {
            return AuthActivity().db.collection("Sources")
                .whereEqualTo("uid", AuthActivity().auth.uid.toString())
        }
    }

}