package com.flyingjetski.budgeteer.ui.add

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.flyingjetski.budgeteer.*
import com.flyingjetski.budgeteer.databinding.FragmentEditIncomeBinding
import com.flyingjetski.budgeteer.models.*
import com.flyingjetski.budgeteer.models.enums.Currency
import java.util.*
import kotlin.collections.ArrayList

class EditIncomeFragment : Fragment() {

    private lateinit var binding: FragmentEditIncomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_income, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        // Instantiation
        val activity = requireActivity()
        val incomeId = arguments?.getString("Id")

        val calendar = Calendar.getInstance()
        val dateListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = monthOfYear
                calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                binding.dateEditText.setText(Common.dateToString(calendar.time))
            }

        // Populate View
        Source.getSource(object: Callback {
            override fun onCallback(value: Any) {
                binding.sourceGridView.adapter = Adapters.SourceGridAdapter(
                    requireContext(),
                    value as ArrayList<Source>,
                )
            }
        })

        IncomeCategory.getIncomeCategory(object: Callback {
            override fun onCallback(value: Any) {
                binding.categoryGridView.adapter = Adapters.CategoryGridAdapter(
                    activity,
                    value as ArrayList<Category>,
                )
            }
        })

        // Set Listeners
        binding.dateEditText.setOnClickListener{
            DatePickerDialog(
                activity,
                dateListener,
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            ).show()
        }

        binding.sourceGridView.setOnItemClickListener{ adapterView: AdapterView<*>, _, position: Int, _ ->
            (adapterView.adapter as Adapters.SourceGridAdapter)
                .selectIcon(position)
        }

        binding.categoryGridView.setOnItemClickListener{ adapterView: AdapterView<*>, _, position: Int, _ ->
            (adapterView.adapter as Adapters.CategoryGridAdapter)
                .selectIcon(position)
        }

        binding.editButton.setOnClickListener{
            Income.updateIncomeById(
                incomeId.toString(),
                Common.stringToDate(binding.dateEditText.text.toString()),
                (binding.sourceGridView.adapter as Adapters.SourceGridAdapter)
                    .selectedSourceId,
                (binding.categoryGridView.adapter as Adapters.CategoryGridAdapter)
                    .selectedCategoryId,
                binding.labelEditText.text.toString(),
                binding.amountEditText.text.toString().toDouble(),
                binding.detailsEditText.text.toString(),
            )
            activity.onBackPressed()
        }

        binding.deleteButton.setOnClickListener{
            Income.deleteIncomeById(incomeId.toString())
            activity.onBackPressed()
        }

        // Actions
        Income.getIncomeById(incomeId.toString(), object: Callback {
            override fun onCallback(value: Any) {
                val income = value as Income

                calendar.set(
                    income.date.year + 1900,
                    income.date.month,
                    income.date.date,
                )

                Source.getSourceById(income.sourceId, object: Callback {
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
                Category.getCategoryById(income.categoryId, object: Callback {
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
                binding.dateEditText.setText(Common.dateToString(income.date))
                binding.labelEditText.setText(income.label)
                binding.amountEditText.setText(income.amount.toString())
                binding.detailsEditText.setText(income.details)
            }
            })
    }

}