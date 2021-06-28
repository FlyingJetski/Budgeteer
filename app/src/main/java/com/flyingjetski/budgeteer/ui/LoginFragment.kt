package com.flyingjetski.budgeteer.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.MainActivity
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.loginButton.setOnClickListener{
            login(
                binding.emailText.text.toString().trim(),
                binding.passwordText.text.toString().trim()
            )
        }
        binding.registerButton.setOnClickListener{ view: View ->
            Navigation.findNavController(view).navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
        binding.forgotPasswordButton.setOnClickListener{ view: View ->
            Navigation.findNavController(view).navigate(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment())
        }
        return binding.root
    }

    private fun login(email: String, password: String) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this.context, "Please enter a valid email.",
                Toast.LENGTH_SHORT).show()
            return
        }
        // [START sign_in_with_email]
        AuthActivity().auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    startActivity(Intent(this.context, MainActivity::class.java))
                    requireActivity().finish()
                    updateUI()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this.context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI()
                }
            }
        // [END sign_in_with_email]
    }

    private fun updateUI() {
        binding.emailText.text.clear()
        binding.passwordText.text.clear()
    }

}