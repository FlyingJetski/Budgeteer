package com.flyingjetski.budgeteer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.flyingjetski.budgeteer.databinding.ActivityAuthBinding
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase

class AuthActivity: AppCompatActivity() {

    // [START declare_auth]
    var auth = Firebase.auth
    // [END declare_auth]


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityAuthBinding>(this, R.layout.activity_auth)
        val navController = this.findNavController(R.id.auth_navigation)
        NavigationUI.setupActionBarWithNavController(this, navController)
        this.supportActionBar?.hide()
    }

    fun sendEmailVerification() {
        // [START send_email_verification]
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                // Email Verification sent
            }
        // [END send_email_verification]
    }

    companion object {
        private const val TAG = "EmailPassword"
    }
}