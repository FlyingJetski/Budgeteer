package com.flyingjetski.budgeteer.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentAddExpenseBinding
import com.flyingjetski.budgeteer.databinding.FragmentBlankBinding
import com.flyingjetski.budgeteer.databinding.FragmentHomeBinding
import com.flyingjetski.budgeteer.ui.auth.LoginFragmentDirections

class BlankFragment : Fragment() {

    private lateinit var binding: FragmentBlankBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_blank, container, false)
        binding.addBudgetButton.setOnClickListener{
            Navigation.findNavController(it).navigate(BlankFragmentDirections.actionBlankFragmentToAddBudgetFragment())
        }
        binding.addExpenseCategoryButton.setOnClickListener{
            Navigation.findNavController(it).navigate(BlankFragmentDirections.actionBlankFragmentToAddExpenseCategoryFragment())
        }
        binding.addExpenseButton.setOnClickListener{
            Navigation.findNavController(it).navigate(BlankFragmentDirections.actionBlankFragmentToAddExpenseFragment())
        }
        binding.addIncomeCategoryButton.setOnClickListener{
            Navigation.findNavController(it).navigate(BlankFragmentDirections.actionBlankFragmentToAddIncomeCategoryFragment())
        }
        binding.addIncomeButton.setOnClickListener{
            Navigation.findNavController(it).navigate(BlankFragmentDirections.actionBlankFragmentToAddIncomeFragment())
        }
        binding.addSavingButton.setOnClickListener{
            Navigation.findNavController(it).navigate(BlankFragmentDirections.actionBlankFragmentToAddSavingFragment())
        }
        binding.addWalletButton.setOnClickListener {
            Navigation.findNavController(it).navigate(BlankFragmentDirections.actionBlankFragmentToAddWalletFragment())
        }

        return binding.root
    }

}