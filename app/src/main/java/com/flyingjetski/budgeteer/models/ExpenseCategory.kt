package com.flyingjetski.budgeteer.models

import android.R
import android.util.Log
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.flyingjetski.budgeteer.Adapters
import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.Callback
import com.flyingjetski.budgeteer.models.enums.CategoryType
import com.flyingjetski.budgeteer.models.enums.SourceType
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
): Category(uid, icon, label, CategoryType.INCOME) {

    constructor(): this(null, 0, "")

    companion object {
        fun insertExpenseCategory(category: ExpenseCategory) {
            AuthActivity().db.collection("Categories").add(category)
        }

        fun getExpenseCategory(callback: Callback) {
            AuthActivity().db.collection("Categories")
                .whereEqualTo("uid", AuthActivity().auth.uid.toString())
                .whereEqualTo("type", CategoryType.EXPENSE)
                .addSnapshotListener{
                        snapshot, _ ->
                    run {
                        if (snapshot != null) {
                            val categories = ArrayList<Category>()
                            val documents = snapshot.documents
                            documents.forEach {
                                val category = it.toObject(ExpenseCategory::class.java)
                                if (category != null) {
                                    category.id = it.id
                                    categories.add(category)
                                }
                            }
                            callback.onCallback(categories)
                        }
                    }
                }
        }
    }
}