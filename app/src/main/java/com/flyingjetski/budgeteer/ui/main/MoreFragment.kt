package com.flyingjetski.budgeteer.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentMoreBinding

class MoreFragment : Fragment() {

    private lateinit var binding: FragmentMoreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false)
        binding.aboutButton.setOnClickListener{
            Log.d("TAG", "here2")
        }
        binding.logoutButton.setOnClickListener{
            logout()
        }
        return binding.root
    }

    private fun logout() {
        AuthActivity().auth.signOut()
        startActivity(Intent(this.context, AuthActivity::class.java))
        requireActivity().finish()
    }

}