package com.flyingjetski.budgeteer.models

import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.Callback
import com.flyingjetski.budgeteer.models.enums.Currency
import com.flyingjetski.budgeteer.models.enums.SourceType
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

        fun getWalletById(id: String, callback: Callback) {
            AuthActivity().db.collection("Sources")
                .document(id).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        var wallet = document.toObject(Wallet::class.java)!!
                        if (wallet != null) {
                            callback.onCallback(wallet)
                        }
                    }
                }
        }

        fun getWallet(callback: Callback) {
            AuthActivity().db.collection("Sources")
                .whereEqualTo("uid", AuthActivity().auth.uid.toString())
                .whereEqualTo("type", SourceType.WALLET)
                .addSnapshotListener{
                        snapshot, _ ->
                    run {
                        if (snapshot != null) {
                            val wallets = ArrayList<Wallet>()
                            val documents = snapshot.documents
                            documents.forEach {
                                val wallet = it.toObject(Wallet::class.java)
                                if (wallet != null) {
                                    wallet.id = it.id
                                    wallets.add(wallet)
                                }
                            }
                            callback.onCallback(wallets)
                        }
                    }
                }
        }
    }

}