package com.flyingjetski.budgeteer.models

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.MainActivity

class User(
    id       : String,
    email    : String,
    password : String,
) {
    val id       = id
    val email    = email
    val password = password

    constructor(): this("", "", "")

    companion object {
        fun insertUser(fragment: Fragment, user: User) {
            AuthActivity().db.collection("Users").add(user)
                .addOnCompleteListener(fragment.requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(fragment.context, "Registered successfully.",
                            Toast.LENGTH_SHORT).show()
                        fragment.startActivity(Intent(fragment.context, MainActivity::class.java))
                        fragment.requireActivity().finish()
                    } else {
                        Toast.makeText(fragment.context, "Register failed, please try again.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

        fun getUserById(id: String) {
            var user = User()
            AuthActivity().db.collection("Users")
                .document(id).set(user)
        }
    }
}