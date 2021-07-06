package com.flyingjetski.budgeteer.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.flyingjetski.budgeteer.Adapters
import com.flyingjetski.budgeteer.Common
import com.flyingjetski.budgeteer.Common.Companion.stringToDate
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentEditBudgetBinding
import com.flyingjetski.budgeteer.models.Budget
import com.flyingjetski.budgeteer.models.IncomeCategory
import com.flyingjetski.budgeteer.models.Source
import com.flyingjetski.budgeteer.models.enums.Currency
import java.lang.reflect.Field

class EditBudgetFragment : Fragment() {

    private lateinit var binding: FragmentEditBudgetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_budget, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        // Instantiation
        var budgetId = arguments?.getString("budgetId")
        val drawablesFields: Array<Field> = R.mipmap::class.java.fields
        val icons: ArrayList<Int> = ArrayList()

        for (field in drawablesFields) {
            try {
                icons.add(field.getInt(null))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Populate View
        binding.categoryGridView.adapter =
            Adapters.CategoryIconGridAdapter(this.requireContext(), icons)
        binding.currencySpinner.adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                Currency.values()
            )


        // Set Listeners
        binding.categoryGridView.setOnItemClickListener{ adapterView: AdapterView<*>, _, position: Int, _ ->
            (adapterView.adapter as Adapters.CategoryIconGridAdapter)
                .selectIcon(position)
        }

        binding.editButton.setOnClickListener{
            Budget.updateBudgetById(
                budgetId.toString(),
                true,
                (binding.categoryGridView.adapter as Adapters.CategoryIconGridAdapter)
                    .selectedIconResource,
                binding.labelEditText.text.toString(),
                binding.currencySpinner.selectedItem as Currency,
                binding.amountEditText.text.toString().toDouble(),
                stringToDate(binding.startDateEditText.text.toString()),
                stringToDate(binding.endDateEditText.text.toString()),
                false,
            )
            Navigation.findNavController(it).navigateUp()
        }

        binding.deleteButton.setOnClickListener{
            IncomeCategory.deleteIncomeCategoryById(budgetId.toString())
            Navigation.findNavController(it).navigateUp()
        }

        // Actions
        Source.getSourceById(budgetId.toString())
            .addOnSuccessListener { document ->
                if (document != null) {
                    var budget = document.toObject(Budget::class.java)!!
                    if (budget != null) {
                        binding.categoryGridView.deferNotifyDataSetChanged()
                        val position = (binding.categoryGridView.adapter as Adapters.CategoryIconGridAdapter)
                            .getPositionOfResource(budget.icon)
                        binding.categoryGridView.performItemClick(
                            binding.categoryGridView,
                            position,
                            binding.categoryGridView.adapter.getItemId(position),
                        )
                        binding.labelEditText.setText(budget.label)
                        for (position in 0 until binding.currencySpinner.count) {
                            if ((binding.currencySpinner.getItemAtPosition(position) as Currency) == budget.currency) {
                                binding.currencySpinner.setSelection(position)
                                break
                            }
                        }
                        binding.amountEditText.setText(budget.amount.toString())
                        binding.startDateEditText.setText(Common.dateToString(budget.startDate))
                        binding.endDateEditText.setText(Common.dateToString(budget.endDate))
                    }
                }
            }
    }

}