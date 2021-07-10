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
import com.flyingjetski.budgeteer.Adapters
import com.flyingjetski.budgeteer.Callback
import com.flyingjetski.budgeteer.Common
import com.flyingjetski.budgeteer.models.Category
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
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        // Instantiation
        val activity = requireActivity()
        val expenseCategoryId = arguments?.getString("Id")

        // Populate View
        binding.categoryGridView.adapter =
            Adapters.IconGridAdapter(this.requireContext(), Common.expenseCategoryIcons)


        // Set Listeners
        binding.categoryGridView.setOnItemClickListener{ adapterView: AdapterView<*>, _, position: Int, _ ->
            (adapterView.adapter as Adapters.IconGridAdapter)
                .selectIcon(position)
        }

        binding.editButton.setOnClickListener{
            Category.updateCategoryById(
                expenseCategoryId.toString(),
                (binding.categoryGridView.adapter as Adapters.IconGridAdapter)
                    .selectedIconResource,
                binding.labelEditText.text.toString(),
            )
            activity.onBackPressed()
        }

        binding.deleteButton.setOnClickListener{
            Category.deleteCategoryById(expenseCategoryId.toString())
            activity.onBackPressed()
        }

        // Actions
        Category.getCategoryById(expenseCategoryId.toString(), object: Callback {
            override fun onCallback(value: Any) {
                val category = value as Category
                binding.categoryGridView.deferNotifyDataSetChanged()
                val position = (binding.categoryGridView.adapter as Adapters.IconGridAdapter)
                    .getPositionOfResource(category.icon)
                binding.categoryGridView.performItemClick(
                    binding.categoryGridView,
                    position,
                    binding.categoryGridView.adapter.getItemId(position),
                )
                binding.labelEditText.setText(category.label)
            }
        })
    }

}