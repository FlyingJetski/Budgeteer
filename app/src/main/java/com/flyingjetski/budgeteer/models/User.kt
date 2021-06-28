package com.flyingjetski.budgeteer.models

import com.flyingjetski.budgeteer.AuthActivity

class User(
    id: String,
    email: String,
    password: String
) {
    private val id = id
    private val email = email
    private val password = password

    constructor(): this("", "", "")

    fun getUserById(id: String) {
        var user = User()
        AuthActivity().db.collection("Users")
            .document(AuthActivity().auth.uid.toString()).set(user)
    }

    fun getEmail(): String {
        return this.email
    }
}