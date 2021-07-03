package com.flyingjetski.budgeteer.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentEditExpenseCategoryBinding
import com.flyingjetski.budgeteer.models.ExpenseCategory
import com.flyingjetski.budgeteer.Adapters
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
        val drawablesFields: Array<Field> = R.mipmap::class.java.fields
        val icons: ArrayList<Int> = ArrayList()

        for (field in drawablesFields) {
            try {
                icons.add(field.getInt(null))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        binding.categoryGridView.setOnItemClickListener{ adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            (adapterView.adapter as Adapters.CategoryIconGridAdapter)
                .selectIcon(i)
        }

        binding.categoryGridView.adapter =
            Adapters.CategoryIconGridAdapter(this.requireContext(), icons)

        ExpenseCategory.getExpenseCategoryById(expenseCategoryId.toString())
            .addOnSuccessListener { document ->
                if (document != null) {
                    var expenseCategory = document.toObject(ExpenseCategory::class.java)!!
                    if (expenseCategory != null) {
                        binding.categoryGridView.deferNotifyDataSetChanged()
                        val position = (binding.categoryGridView.adapter as Adapters.CategoryIconGridAdapter)
                            .getPositionOfResource(expenseCategory.icon)
                        binding.categoryGridView.performItemClick(
                            binding.categoryGridView,
                            position,
                            binding.categoryGridView.adapter.getItemId(position),
                        )
                        binding.label.setText(expenseCategory.label)
                    }
                }
            }

        binding.editButton.setOnClickListener{
            ExpenseCategory.updateExpenseCategoryById(
                expenseCategoryId.toString(),
                (binding.categoryGridView.adapter as Adapters.CategoryIconGridAdapter)
                    .selectedIconResource,
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