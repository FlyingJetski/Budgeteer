package com.flyingjetski.budgeteer.models

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.models.enums.Currency
import com.flyingjetski.budgeteer.models.enums.FrequencyType
import com.flyingjetski.budgeteer.models.enums.SourceType
import com.google.firebase.firestore.Query
import java.util.*

class Saving(
    uid      : String?,
    isActive : Boolean,
    icon     : Int,
    label    : String,
    currency : Currency,
    target   : Double,
    deadline : Date,
    autoSave : AutoSave,
): Source(uid, icon, label, SourceType.SAVING, currency) {
    val isActive = isActive
    val target   = target
    val deadline = deadline
    val autoSave = autoSave

    constructor(): this(null, false, 0, "", Currency.MYR, 0.0, Date(), AutoSave())

    companion object {
        fun insertSaving(source: Saving) {
            AuthActivity().db.collection("Sources").add(source)
        }

        fun updateSavingById(
            id       : String,
            isActive : Boolean?,
            icon     : Int?,
            label    : String?,
            currency : Currency?,
            target   : Double?,
            deadline : Date?,
            autoSave : AutoSave?,
        ) {
            val data = HashMap<String, Any>()
            if (isActive != null) {
                data["isActive"] = isActive
            }
            if (icon != null && icon != 0) {
                data["icon"] = icon
            }
            if (label != null && label != "") {
                data["label"] = label
            }
            if (currency != null) {
                data["currency"] = currency
            }
            if (target != null && target != 0.0) {
                data["target"] = target
            }
            if (deadline != null) {
                data["deadline"] = deadline
            }
            if (autoSave != null) {
                data["autoSave"] = autoSave
            }

            AuthActivity().db.collection("Sources")
                .document(id).update(data)
        }

        fun getSaving(): Query {
            return AuthActivity().db.collection("Sources")
                .whereEqualTo("uid", AuthActivity().auth.uid.toString())
                .whereEqualTo("type", SourceType.SAVING)
        }
    }


}