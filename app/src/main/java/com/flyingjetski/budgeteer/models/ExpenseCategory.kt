package com.flyingjetski.budgeteer.models

import android.R
import android.util.Log
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.models.enums.CategoryType
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject

class ExpenseCategory(
    uid   : String?,
    icon  : Int,
    label : String,
): Category(uid, icon, label, CategoryType.EXPENSE) {

    constructor(): this(null, 0, "")

    companion object {
        fun insertExpenseCategory(category: ExpenseCategory) {
            AuthActivity().db.collection("Categories").add(category)
        }

        fun updateExpenseCategoryById(id: String, icon: Int?, label: String?) {
            val data = HashMap<String, Any>()
            if (icon != null && icon != 0) {
                data["icon"] = icon.toInt()
            }
            if (label != null && label != "") {
                data["label"] = label.toString()
            }
            AuthActivity().db.collection("Categories")
                .document(id).update(data)
        }

        fun deleteExpenseCategoryById(id: String) {
            AuthActivity().db.collection("Categories")
                .document(id).delete()
        }

        fun getExpenseCategoryById(id: String): Task<DocumentSnapshot> {
            return AuthActivity().db.collection("Categories")
                .document(id).get()
        }

        fun getExpenseCategory(): Query {
            return AuthActivity().db.collection("Categories")
                .whereEqualTo("uid", AuthActivity().auth.uid.toString())
        }

        override fun toString(): String {
            return super.toString()
        }
    }
}