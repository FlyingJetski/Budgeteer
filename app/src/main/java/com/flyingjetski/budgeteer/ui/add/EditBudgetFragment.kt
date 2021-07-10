package com.flyingjetski.budgeteer.ui.add

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
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
import com.flyingjetski.budgeteer.Callback
import com.flyingjetski.budgeteer.Common
import com.flyingjetski.budgeteer.Common.Companion.stringToDate
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentEditBudgetBinding
import com.flyingjetski.budgeteer.models.Budget
import com.flyingjetski.budgeteer.models.Category
import com.flyingjetski.budgeteer.models.Source
import com.flyingjetski.budgeteer.models.enums.Currency
import java.lang.reflect.Field
import java.util.*
import kotlin.collections.ArrayList

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
        val budgetId = arguments?.getString("Id")

        val startCalendar = Calendar.getInstance()
        val startDateListener =
            OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                startCalendar[Calendar.YEAR] = year
                startCalendar[Calendar.MONTH] = monthOfYear
                startCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                binding.startDateEditText.setText(Common.dateToString(startCalendar.time))
            }

        val endCalendar = Calendar.getInstance()
        val endDateListener =
            OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                endCalendar[Calendar.YEAR] = year
                endCalendar[Calendar.MONTH] = monthOfYear
                endCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                binding.endDateEditText.setText(Common.dateToString(endCalendar.time))
            }

        // Populate View
        binding.categoryGridView.adapter =
            Adapters.IconGridAdapter(this.requireContext(), Common.sourceIcons)
        binding.currencySpinner.adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                Currency.values()
            )


        // Set Listeners
        binding.categoryGridView.setOnItemClickListener{ adapterView: AdapterView<*>, _, position: Int, _ ->
            (adapterView.adapter as Adapters.IconGridAdapter)
                .selectIcon(position)
        }

        binding.startDateEditText.setOnClickListener{
            DatePickerDialog(
                this.requireContext(),
                startDateListener,
                startCalendar[Calendar.YEAR],
                startCalendar[Calendar.MONTH],
                startCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }

        binding.endDateEditText.setOnClickListener{
            DatePickerDialog(
                this.requireContext(),
                endDateListener,
                endCalendar[Calendar.YEAR],
                endCalendar[Calendar.MONTH],
                endCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }

        binding.editButton.setOnClickListener{
            Budget.updateBudgetById(
                budgetId.toString(),
                true,
                (binding.categoryGridView.adapter as Adapters.IconGridAdapter)
                    .selectedIconResource,
                binding.labelEditText.text.toString(),
                binding.amountEditText.text.toString().toDouble(),
                binding.currencySpinner.selectedItem as Currency,
                stringToDate(binding.startDateEditText.text.toString()),
                stringToDate(binding.endDateEditText.text.toString()),
                false,
            )
            requireActivity().onBackPressed()
        }

        binding.deleteButton.setOnClickListener{
            Budget.deleteBudgetById(budgetId.toString())
            requireActivity().onBackPressed()
        }

        // Actions
        Budget.getBudgetById(budgetId.toString(), object: Callback {
            override fun onCallback(value: Any) {
                val budget = value as Budget
                binding.categoryGridView.deferNotifyDataSetChanged()

                startCalendar.set(
                    budget.startDate.year,
                    budget.startDate.month,
                    budget.startDate.date,
                )

                endCalendar.set(
                    budget.startDate.year,
                    budget.startDate.month,
                    budget.startDate.date,
                )

                val position = (binding.categoryGridView.adapter as Adapters.IconGridAdapter)
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
            })
    }

}