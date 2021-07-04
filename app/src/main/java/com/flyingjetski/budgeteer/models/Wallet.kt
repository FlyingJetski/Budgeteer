package com.flyingjetski.budgeteer.models

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.MainActivity
import com.flyingjetski.budgeteer.models.enums.Currency
import com.flyingjetski.budgeteer.models.enums.SourceType
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import java.util.HashMap

class Wallet(
    uid      : String?,
    icon     : Int,
    label    : String,
    currency : Currency,
    isMain   : Boolean,
): Source(uid, icon, label, SourceType.WALLET, currency) {
    val isMain   = isMain

    constructor(): this(null, 0, "", Currency.MYR, false)

    companion object {
        fun insertWallet(source: Wallet) {
            AuthActivity().db.collection("Sources").add(source)
        }

        fun updateWalletById(
            id: String,
            icon: Int?,
            label: String?,
            currency: Currency?
        ) {
            val data = HashMap<String, Any>()
            if (icon != null && icon != 0) {
                data["icon"] = icon.toInt()
            }
            if (label != null && label != "") {
                data["label"] = label.toString()
            }
            if (currency != null) {
                data["currency"] = currency
            }
            AuthActivity().db.collection("Sources")
                .document(id).update(data)
        }

        fun getWallet(): Query {
            return AuthActivity().db.collection("Sources")
                .whereEqualTo("uid", AuthActivity().auth.uid.toString())
                .whereEqualTo("type", SourceType.WALLET)
        }
    }

}