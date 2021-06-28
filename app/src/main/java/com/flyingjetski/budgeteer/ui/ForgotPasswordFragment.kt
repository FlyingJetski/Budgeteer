package com.flyingjetski.budgeteer.ui

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
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentForgotPasswordBinding

class ForgotPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgotPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password, container, false)
        binding.forgotPasswordButton.setOnClickListener{
            forgotPassword(
                binding.emailText.text.toString().trim(),
            )
        }
        return binding.root
    }

    private fun forgotPassword(email: String) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this.context, "Please enter a valid email.",
                Toast.LENGTH_SHORT).show()
            return
        }
        AuthActivity().auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this.context, "Email has been sent.",
                        Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(this.requireView())
                        .navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToLoginFragment())
                    updateUI()
                }
            }
    }

    private fun updateUI() {
        binding.emailText.text.clear()
    }

}