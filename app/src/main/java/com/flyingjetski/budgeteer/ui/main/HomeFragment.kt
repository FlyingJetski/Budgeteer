package com.flyingjetski.budgeteer.ui.main

//import com.anychart.AnyChart
//import com.anychart.chart.common.dataentry.DataEntry

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.view.allViews
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Pie
import com.flyingjetski.budgeteer.Adapters
import com.flyingjetski.budgeteer.Callback
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentHomeBinding
import com.flyingjetski.budgeteer.models.Category
import com.flyingjetski.budgeteer.models.Expense
import com.flyingjetski.budgeteer.models.Income
import com.flyingjetski.budgeteer.models.Source
import com.flyingjetski.budgeteer.models.enums.Currency
import java.text.DateFormatSymbols
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.exp


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var pie: Pie
    private var previousSelectedSourcePosition = -1
    private var source: Source? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        setupUI()
        return binding.root
    }

    fun setupUI() {
        // Initialization
        val years = ArrayList<Int>()
        val thisYear: Int = Calendar.getInstance().get(Calendar.YEAR)
        for (year in thisYear downTo 1900) {
            years.add(year)
        }

        val quarters = ArrayList<String>()
        for (quarter in 1..4) {
            quarters.add("Q$quarter")
        }

        val dateRanges = ArrayList<String>()
        dateRanges.add("Monthly")
        dateRanges.add("Quarterly")
        dateRanges.add("Yearly")

        val currencies = ArrayList<Currency>()

        pie = AnyChart.pie()
        pie.labels()
            .position("outside")
            .enabled(true)
            .format("{%X}")

        // Populate View
        binding.yearSpinner.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, years)
        binding.monthSpinner.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, DateFormatSymbols().months)
        binding.quarterSpinner.adapter =
            ArrayAdapter(
                requireContext(), android.R.layout.simple_spinner_dropdown_item, quarters
            )
        binding.dateRangeSpinner.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, dateRanges)

        binding.anyChart.setChart(pie)

        Source.getSource(object: Callback {
            override fun onCallback(value: Any) {
                val sources = value as ArrayList<Source>
                sources.forEach { source ->
                    if (!currencies.contains(source.currency)) {
                        currencies.add(source.currency)
                    }
                }
                binding.currencySpinner.adapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, currencies)

                binding.sourceGridView.adapter = Adapters.SourceGridHomeAdapter(
                    requireContext(),
                    sources,
                )
            }
        })

        // Set Listeners
        binding.toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                updatePie(source?.id, source?.label)
            }
        }

        binding.yearSpinner.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?){}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updatePie(source?.id, source?.label)
            }
        }

        binding.monthSpinner.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?){}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updatePie(source?.id, source?.label)
            }
        }

        binding.quarterSpinner.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?){}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updatePie(source?.id, source?.label)
            }
        }

        binding.dateRangeSpinner.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?){}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when ((view as TextView).text.toString()) {
                    "Monthly" -> {
                        binding.monthSpinner.visibility = View.VISIBLE
                        binding.quarterSpinner.visibility = View.INVISIBLE
                    }
                    "Quarterly" -> {
                        binding.monthSpinner.visibility = View.INVISIBLE
                        binding.quarterSpinner.visibility = View.VISIBLE
                    }
                    "Yearly" -> {
                        binding.monthSpinner.visibility = View.INVISIBLE
                        binding.quarterSpinner.visibility = View.INVISIBLE
                    }
                    else -> {}
                }
                updatePie(source?.id, source?.label)
            }
        }

        binding.currencySpinner.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?){}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updatePie(source?.id, source?.label)
            }
        }

        binding.sourceGridView.setOnItemClickListener { _, _, position: Int, _ ->
            if (previousSelectedSourcePosition != position) {
                source = binding.sourceGridView.getItemAtPosition(position) as Source
                previousSelectedSourcePosition = position
                updatePie(source?.id, source?.label)
            } else {
                source = null
                previousSelectedSourcePosition = -1
                updatePie(null, null)
            }
        }

        // Action
        binding.monthSpinner.setSelection(Calendar.getInstance().get(Calendar.MONTH))
        Source.getSource(object: Callback {
            override fun onCallback(value: Any) {
                val sources = value as ArrayList<Source>
                sources.forEach { source ->
                    if (!currencies.contains(source.currency)) {
                        currencies.add(source.currency)
                    }
                }
                binding.currencySpinner.adapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, currencies)

                binding.sourceGridView.adapter = Adapters.SourceGridHomeAdapter(
                    requireContext(),
                    sources,
                )

                updatePie(source?.id, source?.label)
            }
        })
    }

    private fun updatePie(sourceId: String?, legend: String?) {
        var startDate = Date()
        var endDate = Date()

        when (binding.dateRangeSpinner.selectedItem as String) {
            "Monthly" -> {
                startDate = Date(
                    binding.yearSpinner.selectedItem as Int - 1900,
                    binding.monthSpinner.selectedItemPosition,
                    1,
                )
                endDate = Date(
                    binding.yearSpinner.selectedItem as Int - 1900,
                    binding.monthSpinner.selectedItemPosition + 1,
                    1,
                )
            }
            "Quarterly" -> {
                when (binding.quarterSpinner.selectedItem as String) {
                    "Q1" -> {
                        startDate = Date(
                            binding.yearSpinner.selectedItem as Int - 1900,
                            0,
                            1,
                        )
                        endDate = Date(
                            binding.yearSpinner.selectedItem as Int - 1900,
                            3,
                            1,
                        )
                    }
                    "Q2" -> {
                        startDate = Date(
                            binding.yearSpinner.selectedItem as Int - 1900,
                            3,
                            1,
                        )
                        endDate = Date(
                            binding.yearSpinner.selectedItem as Int - 1900,
                            6,
                            1,
                        )
                    }
                    "Q3" -> {
                        startDate = Date(
                            binding.yearSpinner.selectedItem as Int - 1900,
                            6,
                            1,
                        )
                        endDate = Date(
                            binding.yearSpinner.selectedItem as Int - 1900,
                            9,
                            1,
                        )
                    }
                    "Q4" -> {
                        startDate = Date(
                            binding.yearSpinner.selectedItem as Int - 1900,
                            9,
                            1,
                        )
                        endDate = Date(
                            binding.yearSpinner.selectedItem as Int - 1900 + 1,
                            0,
                            1,
                        )
                    }
                }
            }
            "Yearly" -> {
                startDate = Date(
                    binding.yearSpinner.selectedItem as Int - 1900,
                    0,
                    1,
                )
                endDate = Date(
                    binding.yearSpinner.selectedItem as Int - 1900 + 1,
                    0,
                    1,
                )
            }
        }

        when (binding.toggleButton.checkedButtonId) {
            binding.expenseButton.id -> showExpenses(binding.currencySpinner.selectedItem as Currency,
                startDate, endDate, sourceId, legend)
            binding.incomeButton.id -> showIncomes(binding.currencySpinner.selectedItem as Currency,
                startDate, endDate, sourceId, legend)
        }
    }

    private fun showExpenses(currency: Currency, startDate: Date, endDate: Date, sourceId: String?, legend: String?) {
        Expense.getExpense(currency, sourceId, startDate, endDate, object: Callback {
            override fun onCallback(value: Any) {
                val expenses = value as ArrayList<Expense>
                val dataEntries = ArrayList<DataEntry>()
                val dataMap: MutableMap<String, Double> = mutableMapOf()

                expenses.forEach { expense ->
                    dataMap += if (dataMap.containsKey(expense.categoryId)) {
                        Pair(expense.categoryId, dataMap[expense.categoryId]!! + expense.amount)
                    } else {
                        Pair(expense.categoryId, expense.amount)
                    }
                }

                if (dataMap.isEmpty()) {
                    binding.anyChart.visibility = View.INVISIBLE
                } else {
                    binding.anyChart.visibility = View.VISIBLE
                    dataMap.forEach { (k, v) ->
                        Category.getCategoryById(k, object: Callback {
                            override fun onCallback(value: Any) {
                                val category = value as Category
                                dataEntries.add(ValueDataEntry(category.label, v))

                                if (legend != null) pie.title(legend) else pie.title("All Expenses")
                                pie.data(dataEntries)
                            }
                        })
                    }
                }
            }
        })
    }

    private fun showIncomes(currency: Currency, startDate: Date, endDate: Date, sourceId: String?, legend: String?) {
        Income.getIncome(currency, sourceId, startDate, endDate, object: Callback {
            override fun onCallback(value: Any) {
                val incomes = value as ArrayList<Income>
                val dataEntries = ArrayList<DataEntry>()
                val dataMap: MutableMap<String, Double> = mutableMapOf()

                incomes.forEach { income ->
                    dataMap += if (dataMap.containsKey(income.categoryId)) {
                        Pair(income.categoryId, dataMap[income.categoryId]!! + income.amount)
                    } else {
                        Pair(income.categoryId, income.amount)
                    }
                }

                if (dataMap.isEmpty()) {
                    binding.anyChart.visibility = View.INVISIBLE
                } else {
                    binding.anyChart.visibility = View.VISIBLE
                    dataMap.forEach { (k, v) ->
                        Category.getCategoryById(k, object: Callback {
                            override fun onCallback(value: Any) {
                                val category = value as Category
                                dataEntries.add(ValueDataEntry(category.label, v))

                                if (legend != null) pie.title(legend) else pie.title("All Incomes")
                                pie.data(dataEntries)
                            }
                        })
                    }
                }
            }
        })
    }

}