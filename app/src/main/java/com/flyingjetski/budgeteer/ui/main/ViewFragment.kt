package com.flyingjetski.budgeteer.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.flyingjetski.budgeteer.BlankActivity
import com.flyingjetski.budgeteer.MainActivity
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentStatisticsBinding
import com.flyingjetski.budgeteer.databinding.FragmentViewBinding
import com.flyingjetski.budgeteer.models.Budget
import com.flyingjetski.budgeteer.models.Saving
import com.flyingjetski.budgeteer.models.Wallet

class ViewFragment : Fragment() {

    private lateinit var binding: FragmentViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        // Set Listeners
        binding.viewExpensesButton.setOnClickListener{ view ->
            val intent = Intent(requireContext(), BlankActivity::class.java)
            intent.putExtra("Fragment", "ViewExpense")
            startActivity(intent)
        }

        binding.viewIncomesButton.setOnClickListener{ view ->
            val intent = Intent(requireContext(), BlankActivity::class.java)
            intent.putExtra("Fragment", "ViewIncome")
            startActivity(intent)
        }

        binding.viewExpenseCategoriesButton.setOnClickListener{ view ->
            val intent = Intent(requireContext(), BlankActivity::class.java)
            intent.putExtra("Fragment", "ViewExpenseCategory")
            startActivity(intent)
        }

        binding.viewIncomeCategoriesButton.setOnClickListener{ view ->
            val intent = Intent(requireContext(), BlankActivity::class.java)
            intent.putExtra("Fragment", "ViewIncomeCategory")
            startActivity(intent)
        }
    }

}