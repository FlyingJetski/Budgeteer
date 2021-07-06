package com.flyingjetski.budgeteer.ui.add

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.flyingjetski.budgeteer.Adapters
import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.Common
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentAddBudgetBinding
import com.flyingjetski.budgeteer.models.Budget
import com.flyingjetski.budgeteer.models.enums.Currency
import java.lang.reflect.Field
import java.util.*
import kotlin.collections.ArrayList

class AddBudgetFragment : Fragment() {

    private lateinit var binding: FragmentAddBudgetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_budget, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        // Instantiation
        val drawablesFields: Array<Field> = R.mipmap::class.java.fields
        val icons: ArrayList<Int> = ArrayList()

        for (field in drawablesFields) {
            try {
                icons.add(field.getInt(null))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

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
            Adapters.CategoryIconGridAdapter(this.requireContext(), icons)
        binding.currencySpinner.adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                Currency.values()
            )

        // Set Listeners
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

        binding.addButton.setOnClickListener {
            Budget.insertBudget(
                Budget(
                    AuthActivity().auth.uid.toString(),
                    true,
                    (binding.categoryGridView.adapter as
                            Adapters.CategoryIconGridAdapter).selectedIconResource,
                    binding.labelEditText.text.toString(),
                    binding.currencySpinner.selectedItem as Currency,
                    binding.amountEditText.text.toString().toDouble(),
                    startCalendar.time,
                    endCalendar.time,
                    false
                )
            )
            Navigation.findNavController(it).navigateUp()
        }
    }

}