package com.flyingjetski.budgeteer.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentAddExpenseBinding
import com.flyingjetski.budgeteer.databinding.FragmentAddExpenseCategoryBinding
import com.flyingjetski.budgeteer.databinding.FragmentHomeBinding
import com.flyingjetski.budgeteer.models.ExpenseCategory

class AddExpenseCategoryFragment : Fragment() {

    private lateinit var binding: FragmentAddExpenseCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_expense_category, container, false)

        binding.addButton.setOnClickListener{
            ExpenseCategory.insertExpenseCategory(this,
                ExpenseCategory(null, binding.icon.text.toString(), binding.label.text.toString())
            )
        }

        return binding.root
    }

}