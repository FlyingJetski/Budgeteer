package com.flyingjetski.budgeteer.ui.add

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.flyingjetski.budgeteer.Adapters
import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.Common
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentAddExpenseBinding
import com.flyingjetski.budgeteer.models.*
import com.flyingjetski.budgeteer.models.enums.Currency
import com.flyingjetski.budgeteer.models.enums.Feedback
import java.util.*
import kotlin.collections.ArrayList


class AddExpenseFragment : Fragment() {

    private lateinit var binding: FragmentAddExpenseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_expense, container, false)
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
                binding.dateEditText.setText(Common.updateLabel(calendar.time))
            }

        // Populate View
        ExpenseCategory.getExpenseCategory()
            .addSnapshotListener{
                    snapshot, _ ->
                run {
                    if (snapshot != null) {
                        val categories = ArrayList<ExpenseCategory>()
                        val documents = snapshot.documents
                        documents.forEach {
                            val category = it.toObject(ExpenseCategory::class.java)
                            if (category != null) {
                                category.id = it.id
                                categories.add(category)
                            }
                        }
                        binding.categoryGridView.adapter =
                            Adapters.CategoryGridAdapter(
                                this.requireContext(),
                                categories as ArrayList<Category>,
                            )
                    }
                }
            }

        Source.getSource()
            .addSnapshotListener{
                    snapshot, _ ->
                run {
                    if (snapshot != null) {
                        val sources = ArrayList<Source>()
                        val documents = snapshot.documents
                        documents.forEach {
                            val source = it.toObject(Source::class.java)
                            if (source != null) {
                                source.id = it.id
                                sources.add(source)
                            }
                        }
                        binding.sourceGridView.adapter =
                            Adapters.SourceGridAdapter(
                                this.requireContext(),
                                sources,
                            )
                    }
                }
            }


        // Set Listener
        binding.dateEditText.setOnClickListener{
            DatePickerDialog(
                this.requireContext(), calendarListener, calendar[Calendar.YEAR], calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            ).show()
        }

        binding.addButton.setOnClickListener {
            Expense.insertExpense(
                Expense(
                    AuthActivity().auth.uid.toString(),
                    calendar.time,
                    (binding.sourceGridView.adapter as Adapters.SourceGridAdapter).selectedSourceId.toString(),
                    null,
                    Currency.MYR,
                    (binding.categoryGridView.adapter as Adapters.CategoryGridAdapter).selectedCategoryId.toString(),
                    null,
                    "first expense",
                    100.0,
                    null,
                    Feedback.NEUTRAL,
                )
            )
            Navigation.findNavController(it).navigateUp()
        }

    }

}