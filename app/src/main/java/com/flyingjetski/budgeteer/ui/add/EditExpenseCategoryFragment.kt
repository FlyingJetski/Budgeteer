package com.flyingjetski.budgeteer.ui.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentEditExpenseBinding
import com.flyingjetski.budgeteer.databinding.FragmentEditExpenseCategoryBinding
import com.flyingjetski.budgeteer.databinding.FragmentHomeBinding
import com.flyingjetski.budgeteer.models.ExpenseCategory
import java.lang.reflect.Field

class EditExpenseCategoryFragment : Fragment() {

    private lateinit var binding: FragmentEditExpenseCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_expense_category, container, false)

        var expenseCategoryId = arguments?.getString("expenseCategoryId")
        var selectedIconResource: Int = 0
        ExpenseCategory.getExpenseCategoryById(expenseCategoryId.toString())
        .addOnSuccessListener { document ->
            if (document != null) {
                var expenseCategory = document.toObject(ExpenseCategory::class.java)!!
                if (expenseCategory != null) {
                    selectedIconResource = expenseCategory.icon
                    binding.selectedIcon.setImageResource(selectedIconResource)
                    binding.label.setText(expenseCategory.label)
                }
            }
        }

        val drawablesFields: Array<Field> = R.mipmap::class.java.fields
        val icons: ArrayList<Int> = ArrayList()

        for (field in drawablesFields) {
            try {
                icons.add(field.getInt(null))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        binding.gridView.adapter =
            AddExpenseCategoryFragment.GridViewAdapter(this.requireContext(), icons)
        binding.gridView.setOnItemClickListener { parent, view, position, id ->
            selectedIconResource = icons[position]
            binding.selectedIcon.setImageResource(selectedIconResource)
        }

        binding.editButton.setOnClickListener{
            ExpenseCategory.updateExpenseCategoryById(
                expenseCategoryId.toString(),
                selectedIconResource,
                binding.label.text.toString(),
            )
            Navigation.findNavController(it).navigateUp()
        }

        binding.deleteButton.setOnClickListener{
            ExpenseCategory.deleteExpenseCategoryById(expenseCategoryId.toString())
            Navigation.findNavController(it).navigateUp()
        }

        return binding.root
    }

}