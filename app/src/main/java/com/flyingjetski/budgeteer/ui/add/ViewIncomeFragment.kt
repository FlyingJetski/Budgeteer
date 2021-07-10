package com.flyingjetski.budgeteer.ui.add

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.Adapters
import com.flyingjetski.budgeteer.BlankActivity
import com.flyingjetski.budgeteer.Callback
import com.flyingjetski.budgeteer.databinding.FragmentViewExpenseBinding
import com.flyingjetski.budgeteer.databinding.FragmentViewIncomeBinding
import com.flyingjetski.budgeteer.models.Budget
import com.flyingjetski.budgeteer.models.Expense
import com.flyingjetski.budgeteer.models.Income
import java.text.DateFormatSymbols
import java.util.*
import kotlin.collections.ArrayList

class ViewIncomeFragment : Fragment() {

    lateinit var binding: FragmentViewIncomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_income, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        // Initialization
        val activity = requireActivity()

        val years = ArrayList<Int>()
        val thisYear: Int = Calendar.getInstance().get(Calendar.YEAR)
        for (year in thisYear downTo 1900) {
            years.add(year)
        }

        // Populate View
        binding.yearSpinner.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, years)
        binding.monthSpinner.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, DateFormatSymbols().months)

        // Set Listeners
        binding.yearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?){}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateAdapter()
            }
        }

        binding.monthSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?){}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateAdapter()
            }
        }

        binding.listView.setOnItemClickListener{adapterView, view, position, id ->
            (requireActivity() as BlankActivity).navigateToFragment(EditIncomeFragment(),
                (binding.listView.adapter.getItem(position) as Income).id.toString())
        }

        binding.addIncomeButton.setOnClickListener{ view ->
            (requireActivity() as BlankActivity).navigateToFragment(AddIncomeFragment(), null)
        }

        // Action
        binding.monthSpinner.setSelection(Calendar.getInstance().get(Calendar.MONTH))
        updateAdapter()
    }

    private fun updateAdapter() {
        val activity = requireActivity()

        val startDate = Date(
            binding.yearSpinner.selectedItem as Int - 1900,
            binding.monthSpinner.selectedItemPosition,
            1,
        )
        val endDate = Date(
            binding.yearSpinner.selectedItem as Int - 1900,
            binding.monthSpinner.selectedItemPosition + 1,
            1,
        )

        Income.getIncome(null, null, startDate, endDate, object: Callback {
            override fun onCallback(value: Any) {
                binding.listView.adapter = Adapters.IncomeListAdapter(
                    activity,
                    value as ArrayList<Income>,
                )
            }
        })
    }

}