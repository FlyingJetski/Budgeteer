package com.flyingjetski.budgeteer.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.MainActivity
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentRegisterBinding
import com.flyingjetski.budgeteer.models.User

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        binding.registerButton.setOnClickListener{
            register(
                binding.emailText.text.toString().trim(),
                binding.passwordText.text.toString().trim(),
                binding.confirmPasswordText.text.toString().trim(),
            )
        }
        return binding.root
    }

    private fun register(email: String, password: String, confirmPassword: String) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this.context, "Please enter a valid email.",
                Toast.LENGTH_SHORT).show()
            return
        }
        if (password.length < 6) {
            Toast.makeText(this.context, "Password must be at least 6 characters long.",
                Toast.LENGTH_SHORT).show()
            return
        }
        if (password != confirmPassword) {
            Toast.makeText(this.context, "Password mismatch.",
                Toast.LENGTH_SHORT).show()
            return
        }
        // [START create_user_with_email]
        AuthActivity().auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = User(AuthActivity().auth.uid.toString(), email, password)
                    User.insertUser(this, user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this.context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI()
                }
            }
        // [END create_user_with_email]
    }

    private fun updateUI() {
        binding.emailText.text.clear()
        binding.passwordText.text.clear()
        binding.confirmPasswordText.text.clear()
    }

}