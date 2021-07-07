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
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentEditExpenseBinding
import com.flyingjetski.budgeteer.models.Category
import com.flyingjetski.budgeteer.models.Expense
import com.flyingjetski.budgeteer.models.ExpenseCategory
import com.flyingjetski.budgeteer.models.Source
import com.flyingjetski.budgeteer.models.enums.Currency
import com.flyingjetski.budgeteer.models.enums.Feedback
import java.lang.reflect.Field

class EditExpenseFragment : Fragment() {

    private lateinit var binding: FragmentEditExpenseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_expense, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        // Instantiation
        var expenseId = arguments?.getString("expenseId")

        // Populate View
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

        binding.currencySpinner.adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                Currency.values()
            )

        // Set Listeners
        binding.sourceGridView.setOnItemClickListener{ adapterView: AdapterView<*>, _, position: Int, _ ->
            (adapterView.adapter as Adapters.SourceGridAdapter)
                .selectIcon(position)
        }

        binding.categoryGridView.setOnItemClickListener{ adapterView: AdapterView<*>, _, position: Int, _ ->
            (adapterView.adapter as Adapters.CategoryGridAdapter)
                .selectIcon(position)
        }

        binding.editButton.setOnClickListener{
            Expense.updateExpenseById(
                expenseId.toString(),
                Common.stringToDate(binding.dateEditText.text.toString()),
                (binding.sourceGridView.adapter as Adapters.SourceGridAdapter)
                    .selectedSourceId,
                binding.currencySpinner.selectedItem as Currency,
                (binding.categoryGridView.adapter as Adapters.CategoryGridAdapter)
                    .selectedCategoryId,
                binding.labelEditText.text.toString(),
                binding.amountEditText.text.toString().toDouble(),
                binding.detailsEditText.text.toString(),
                Feedback.NEUTRAL,
            )
            Navigation.findNavController(it).navigateUp()
        }

        binding.deleteButton.setOnClickListener{
            Expense.deleteExpenseById(expenseId.toString())
            Navigation.findNavController(it).navigateUp()
        }

        // Actions
        Expense.getExpenseById(expenseId.toString())
            .addOnSuccessListener { document ->
                if (document != null) {
                    var expense = document.toObject(Expense::class.java)!!
                    if (expense != null) {
                        Source.getSourceById(expense.sourceId)
                            .addOnSuccessListener { document ->
                                if (document != null) {
                                    var source = document.toObject(Source::class.java)!!
                                    if (source != null) {
                                        binding.sourceGridView.deferNotifyDataSetChanged()
                                        val position = (binding.sourceGridView.adapter as Adapters.SourceGridAdapter)
                                            .getPositionOfResource(source.icon)
                                        binding.sourceGridView.performItemClick(
                                            binding.sourceGridView,
                                            position,
                                            binding.sourceGridView.adapter.getItemId(position),
                                        )
                                    }
                                }
                            }
                        ExpenseCategory.getExpenseCategoryById(expense.categoryId)
                            .addOnSuccessListener { document ->
                                if (document != null) {
                                    var expenseCategory = document.toObject(ExpenseCategory::class.java)!!
                                    if (expenseCategory != null) {
                                        binding.categoryGridView.deferNotifyDataSetChanged()
                                        val position = (binding.categoryGridView.adapter as Adapters.CategoryGridAdapter)
                                            .getPositionOfResource(expenseCategory.icon)
                                        binding.categoryGridView.performItemClick(
                                            binding.categoryGridView,
                                            position,
                                            binding.categoryGridView.adapter.getItemId(position),
                                        )
                                        binding.labelEditText.setText(expenseCategory.label)
                                    }
                                }
                            }
                        binding.dateEditText.setText(Common.dateToString(expense.date))
                        binding.labelEditText.setText(expense.label)
                        binding.amountEditText.setText(expense.amount.toString())
                        for (position in 0 until binding.currencySpinner.count) {
                            if ((binding.currencySpinner.getItemAtPosition(position) as Currency) == expense.currency) {
                                binding.currencySpinner.setSelection(position)
                                break
                            }
                        }
                        binding.detailsEditText.setText(expense.details)
                    }
                }
            }
    }

}