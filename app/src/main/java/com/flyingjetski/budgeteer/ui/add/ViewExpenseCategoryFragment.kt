package com.flyingjetski.budgeteer.ui.add

import android.content.Intent
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentViewExpenseCategoryBinding
import com.flyingjetski.budgeteer.models.ExpenseCategory
import com.flyingjetski.budgeteer.Adapters
import com.flyingjetski.budgeteer.BlankActivity
import com.flyingjetski.budgeteer.Callback
import com.flyingjetski.budgeteer.models.Category
import com.flyingjetski.budgeteer.models.Expense
import com.flyingjetski.budgeteer.models.Income

class ViewExpenseCategoryFragment : Fragment() {

    lateinit var binding: FragmentViewExpenseCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_expense_category, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        // Initialization
        val activity = requireActivity()

        // Populate View
        ExpenseCategory.getExpenseCategory(object: Callback {
            override fun onCallback(value: Any) {
                binding.listView.adapter = Adapters.CategoryListAdapter(
                    activity,
                    value as ArrayList<Category>,
                )
            }
        })

        // Set Listeners
        binding.listView.setOnItemClickListener{adapterView, view, position, id ->
            (requireActivity() as BlankActivity).navigateToFragment(EditExpenseCategoryFragment(),
                (binding.listView.adapter.getItem(position) as ExpenseCategory).id.toString())
        }

        binding.addExpenseCategoryButton.setOnClickListener{ view ->
            (requireActivity() as BlankActivity).navigateToFragment(AddExpenseCategoryFragment(), null)
        }
    }

}