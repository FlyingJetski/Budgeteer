package com.flyingjetski.budgeteer.models

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.models.enums.Currency
import com.flyingjetski.budgeteer.models.enums.FrequencyType
import com.flyingjetski.budgeteer.models.enums.SourceType
import java.util.*

class Saving(
    id       : String?,
    isActive : Boolean,
    icon     : String,
    label    : String,
    currency : Currency,
    target   : Double,
    deadline : Date,
    autoSave : AutoSave,
): Source(id, icon, label, SourceType.SAVING, currency) {
    val isActive = isActive
    val target   = target
    val deadline = deadline
    val autoSave = autoSave

    constructor(): this(null, false, "", "", Currency.MYR, 0.0, Date(), AutoSave(Date(), 0.0, 0, FrequencyType.MONTH))

    companion object {
        fun insertSaving(fragment: Fragment, source: Saving) {
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

        fun getSavingById(id: String) {
            var source = Saving()
            AuthActivity().db.collection("Sources")
                .document(id).set(source)
        }
    }


}