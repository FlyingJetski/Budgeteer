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
import com.flyingjetski.budgeteer.*
import com.flyingjetski.budgeteer.databinding.FragmentAddIncomeBinding
import com.flyingjetski.budgeteer.models.*
import com.flyingjetski.budgeteer.models.enums.Currency
import com.flyingjetski.budgeteer.models.enums.Feedback
import java.util.*
import kotlin.collections.ArrayList

class AddIncomeFragment : Fragment() {

    private lateinit var binding: FragmentAddIncomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_income, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        // Instantiation
        val calendar = Calendar.getInstance()
        val calendarListener =
            OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = monthOfYear
                calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                binding.dateEditText.setText(Common.dateToString(calendar.time))
            }

        // Populate View
        IncomeCategory.getIncomeCategory(object: Callback {
            override fun onCallback(value: Any) {
                binding.categoryGridView.adapter = Adapters.CategoryGridAdapter(
                    requireContext(),
                    value as ArrayList<Category>,
                )
            }
        })

        Source.getSource(object: Callback {
            override fun onCallback(value: Any) {
                binding.sourceGridView.adapter = Adapters.SourceGridAdapter(
                    requireContext(),
                    value as ArrayList<Source>,
                )
            }
        })


        // Set Listener
        binding.dateEditText.setOnClickListener{
            DatePickerDialog(
                this.requireContext(), calendarListener, calendar[Calendar.YEAR], calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            ).show()
        }

        binding.addButton.setOnClickListener {
            Income.insertIncome(
                Income(
                    AuthActivity().auth.uid.toString(),
                    calendar.time,
                    (binding.sourceGridView.adapter as Adapters.SourceGridAdapter).selectedSourceId.toString(),
                    null,
                    (binding.categoryGridView.adapter as Adapters.CategoryGridAdapter).selectedCategoryId.toString(),
                    null,
                    binding.labelEditText.text.toString(),
                    binding.amountEditText.text.toString().toDouble(),
                    binding.detailsEditText.text.toString(),
                )
            )
            requireActivity().finish()
        }

        // Actions
        val today = Calendar.getInstance()
        calendar.set(
            today.time.year,
            today.time.month,
            today.time.date,
        )
    }
}