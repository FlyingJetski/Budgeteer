package com.flyingjetski.budgeteer.models

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.Callback
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

        fun getIncomeCategory(callback: Callback) {
            AuthActivity().db.collection("Categories")
                .whereEqualTo("uid", AuthActivity().auth.uid.toString())
                .whereEqualTo("type", CategoryType.INCOME)
                .addSnapshotListener{
                        snapshot, _ ->
                    run {
                        if (snapshot != null) {
                            val categories = ArrayList<Category>()
                            val documents = snapshot.documents
                            documents.forEach {
                                val category = it.toObject(IncomeCategory::class.java)
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