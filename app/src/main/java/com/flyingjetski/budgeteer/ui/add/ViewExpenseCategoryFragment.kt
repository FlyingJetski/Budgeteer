package com.flyingjetski.budgeteer.ui.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.adapters.ExpenseCategoryAdapter
import com.flyingjetski.budgeteer.databinding.FragmentAddExpenseBinding
import com.flyingjetski.budgeteer.databinding.FragmentAddExpenseCategoryBinding
import com.flyingjetski.budgeteer.databinding.FragmentHomeBinding
import com.flyingjetski.budgeteer.databinding.FragmentViewExpenseCategoryBinding
import com.flyingjetski.budgeteer.models.ExpenseCategory

class ViewExpenseCategoryFragment : Fragment() {

    lateinit var binding: FragmentViewExpenseCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_expense_category, container, false)
        val list = mutableListOf<ExpenseCategory>()
        list.add(
            ExpenseCategory(null, "icon_1", "label_1")
        )
        list.add(
            ExpenseCategory(null, "icon_2", "label_2")
        )
        list.add(
            ExpenseCategory(null, "icon_3", "label_3")
        )
        Log.d("TAG", list.toString())

        binding.listView.adapter = ExpenseCategoryAdapter(this.requireContext(),
            list as ArrayList<ExpenseCategory>
        )

        binding.listView.setOnItemClickListener(){adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            Toast.makeText(this.requireActivity(), "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()
        }

        return binding.root
    }

}