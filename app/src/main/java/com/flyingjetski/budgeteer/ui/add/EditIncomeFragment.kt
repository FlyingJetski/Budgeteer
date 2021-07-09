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
import com.flyingjetski.budgeteer.Callback
import com.flyingjetski.budgeteer.Common
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentEditIncomeBinding
import com.flyingjetski.budgeteer.models.*
import com.flyingjetski.budgeteer.models.enums.Currency

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
        var incomeId = arguments?.getString("incomeId")

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
            Navigation.findNavController(it).navigateUp()
        }

        binding.deleteButton.setOnClickListener{
            Income.deleteIncomeById(incomeId.toString())
            Navigation.findNavController(it).navigateUp()
        }

        // Actions
        Income.getIncomeById(incomeId.toString(), object: Callback {
            override fun onCallback(value: Any) {
                val income = value as Income
                if (income != null) {
                        Source.getSourceById(income.sourceId, object: Callback {
                            override fun onCallback(value: Any) {
                                val source = value as Source
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
                            })
                        Category.getCategoryById(income.categoryId, object: Callback {
                            override fun onCallback(value: Any) {
                                val category = value as Category
                                    if (category != null) {
                                        binding.categoryGridView.deferNotifyDataSetChanged()
                                        val position = (binding.categoryGridView.adapter as Adapters.CategoryGridAdapter)
                                            .getPositionOfResource(category.icon)
                                        binding.categoryGridView.performItemClick(
                                            binding.categoryGridView,
                                            position,
                                            binding.categoryGridView.adapter.getItemId(position),
                                        )
                                        binding.labelEditText.setText(category.label)
                                    }
                                }
                            })
                        binding.dateEditText.setText(Common.dateToString(income.date))
                        binding.labelEditText.setText(income.label)
                        binding.amountEditText.setText(income.amount.toString())
                        binding.detailsEditText.setText(income.details)
                    }
                }
            })
    }

}