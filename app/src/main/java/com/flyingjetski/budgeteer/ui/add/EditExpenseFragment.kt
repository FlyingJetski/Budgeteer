package com.flyingjetski.budgeteer.ui.add

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
import com.flyingjetski.budgeteer.Adapters
import com.flyingjetski.budgeteer.Callback
import com.flyingjetski.budgeteer.Common
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentEditExpenseBinding
import com.flyingjetski.budgeteer.models.Category
import com.flyingjetski.budgeteer.models.Expense
import com.flyingjetski.budgeteer.models.ExpenseCategory
import com.flyingjetski.budgeteer.models.Source
import com.flyingjetski.budgeteer.models.enums.Currency
import com.flyingjetski.budgeteer.models.enums.Feedback

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
        val expenseId = arguments?.getString("Id")

        // Populate View
        Source.getSource(object: Callback {
            override fun onCallback(value: Any) {
                binding.sourceGridView.adapter = Adapters.SourceGridAdapter(
                    requireContext(),
                    value as ArrayList<Source>,
                )
            }
        })

        ExpenseCategory.getExpenseCategory(object: Callback {
            override fun onCallback(value: Any) {
                binding.categoryGridView.adapter = Adapters.CategoryGridAdapter(
                    requireContext(),
                    value as ArrayList<Category>,
                )
            }
        })

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
                (binding.categoryGridView.adapter as Adapters.CategoryGridAdapter)
                    .selectedCategoryId,
                binding.labelEditText.text.toString(),
                binding.amountEditText.text.toString().toDouble(),
                binding.detailsEditText.text.toString(),
                Feedback.NEUTRAL,
            )
            requireActivity().finish()
        }

        binding.deleteButton.setOnClickListener{
            Expense.deleteExpenseById(expenseId.toString())
            Navigation.findNavController(it).navigateUp()
        }

        // Actions
        Expense.getExpenseById(expenseId.toString(), object: Callback {
            override fun onCallback(value: Any) {
                val expense = value as Expense
                Source.getSourceById(expense.sourceId, object: Callback {
                    override fun onCallback(value: Any) {
                        val source = value as Source
                        binding.sourceGridView.deferNotifyDataSetChanged()
                        val position = (binding.sourceGridView.adapter as Adapters.SourceGridAdapter)
                            .getPositionOfResource(source.icon)
                        binding.sourceGridView.performItemClick(
                            binding.sourceGridView,
                            position,
                            binding.sourceGridView.adapter.getItemId(position),
                        )
                    }
                })
                Category.getCategoryById(expense.categoryId, object: Callback {
                    override fun onCallback(value: Any) {
                        val category = value as Category
                        binding.categoryGridView.deferNotifyDataSetChanged()
                        val position = (binding.categoryGridView.adapter as Adapters.CategoryGridAdapter)
                            .getPositionOfResource(category.icon)
                        binding.categoryGridView.performItemClick(
                            binding.categoryGridView,
                            position,
                            binding.categoryGridView.adapter.getItemId(position),
                        )
                    }
                })
                binding.dateEditText.setText(Common.dateToString(expense.date))
                binding.labelEditText.setText(expense.label)
                binding.amountEditText.setText(expense.amount.toString())
                binding.detailsEditText.setText(expense.details)
            }
        })
    }

}