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

        IncomeCategory.getIncomeCategory()
            .addSnapshotListener{
                    snapshot, _ ->
                run {
                    if (snapshot != null) {
                        val categories = ArrayList<IncomeCategory>()
                        val documents = snapshot.documents
                        documents.forEach {
                            val category = it.toObject(IncomeCategory::class.java)
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
            Income.updateIncomeById(
                incomeId.toString(),
                Common.stringToDate(binding.dateEditText.text.toString()),
                (binding.sourceGridView.adapter as Adapters.SourceGridAdapter)
                    .selectedSourceId,
                binding.currencySpinner.selectedItem as Currency,
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
        Income.getIncomeById(incomeId.toString())
            .addOnSuccessListener { document ->
                if (document != null) {
                    var income = document.toObject(Income::class.java)!!
                    if (income != null) {
                        Source.getSourceById(income.sourceId)
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
                        IncomeCategory.getIncomeCategoryById(income.categoryId)
                            .addOnSuccessListener { document ->
                                if (document != null) {
                                    var incomeCategory = document.toObject(IncomeCategory::class.java)!!
                                    if (incomeCategory != null) {
                                        binding.categoryGridView.deferNotifyDataSetChanged()
                                        val position = (binding.categoryGridView.adapter as Adapters.CategoryGridAdapter)
                                            .getPositionOfResource(incomeCategory.icon)
                                        binding.categoryGridView.performItemClick(
                                            binding.categoryGridView,
                                            position,
                                            binding.categoryGridView.adapter.getItemId(position),
                                        )
                                        binding.labelEditText.setText(incomeCategory.label)
                                    }
                                }
                            }
                        binding.dateEditText.setText(Common.dateToString(income.date))
                        binding.labelEditText.setText(income.label)
                        binding.amountEditText.setText(income.amount.toString())
                        for (position in 0 until binding.currencySpinner.count) {
                            if ((binding.currencySpinner.getItemAtPosition(position) as Currency) == income.currency) {
                                binding.currencySpinner.setSelection(position)
                                break
                            }
                        }
                        binding.detailsEditText.setText(income.details)
                    }
                }
            }
    }

}