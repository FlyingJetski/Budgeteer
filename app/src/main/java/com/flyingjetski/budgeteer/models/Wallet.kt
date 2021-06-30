package com.flyingjetski.budgeteer.models

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.MainActivity
import com.flyingjetski.budgeteer.models.enums.Currency
import com.flyingjetski.budgeteer.models.enums.SourceType

class Wallet(
    id       : String?,
    icon     : String,
    label    : String,
    currency : Currency,
    isMain   : Boolean,
): Source(id, icon, label, SourceType.WALLET, currency) {
    val isMain   = isMain

    constructor(): this(null, "", "", Currency.MYR, false)

    companion object {
        fun insertWallet(fragment: Fragment, source: Wallet) {
            AuthActivity().db.collection("Sources").add(source)
                .addOnCompleteListener(fragment.requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(fragment.context, "Added successfully.",
                            Toast.LENGTH_SHORT).show()
                        fragment.requireActivity().finish()
                    } else {
                        Toast.makeText(fragment.context, "Addition failed, please try again.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

        fun getWalletById(id: String) {
            var source = Wallet()
            AuthActivity().db.collection("Sources")
                .document(id).set(source)
        }
    }

}