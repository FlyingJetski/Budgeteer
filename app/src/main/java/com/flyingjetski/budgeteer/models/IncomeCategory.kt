package com.flyingjetski.budgeteer.models

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.models.enums.CategoryType
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query

class IncomeCategory(
    uid   : String?,
    icon  : Int,
    label : String,
): Category(uid, icon, label, CategoryType.INCOME) {

    constructor(): this(null, 0, "")

    companion object {
        fun insertIncomeCategory(category: IncomeCategory) {
            AuthActivity().db.collection("Categories").add(category)
        }

        fun updateIncomeCategoryById(id: String, icon: Int?, label: String?) {
            val data = HashMap<String, Any>()
            if (icon != null && icon != 0) {
                data["icon"] = icon
            }
            if (label != null && label != "") {
                data["label"] = label
            }
            AuthActivity().db.collection("Categories")
                .document(id).update(data)
        }

        fun deleteIncomeCategoryById(id: String) {
            AuthActivity().db.collection("Categories")
                .document(id).delete()
        }

        fun getIncomeCategoryById(id: String): Task<DocumentSnapshot> {
            return AuthActivity().db.collection("Categories")
                .document(id).get()
        }

        fun getIncomeCategory(): Query {
            return AuthActivity().db.collection("Categories")
                .whereEqualTo("uid", AuthActivity().auth.uid.toString())
        }
    }


}