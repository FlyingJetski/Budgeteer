package com.flyingjetski.budgeteer.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.flyingjetski.budgeteer.Adapters
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentEditBudgetBinding
import com.flyingjetski.budgeteer.databinding.FragmentHomeBinding
import com.flyingjetski.budgeteer.models.IncomeCategory
import java.lang.reflect.Field

class EditBudgetFragment : Fragment() {

    private lateinit var binding: FragmentEditBudgetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_budget, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        // Instantiation
        var incomeCategoryId = arguments?.getString("incomeCategoryId")
        val drawablesFields: Array<Field> = R.mipmap::class.java.fields
        val icons: ArrayList<Int> = ArrayList()

        for (field in drawablesFields) {
            try {
                icons.add(field.getInt(null))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Populate View
        binding.categoryGridView.adapter =
            Adapters.CategoryIconGridAdapter(this.requireContext(), icons)


        // Set Listeners
        binding.categoryGridView.setOnItemClickListener{ adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            (adapterView.adapter as Adapters.CategoryIconGridAdapter)
                .selectIcon(i)
        }

        binding.editButton.setOnClickListener{
            IncomeCategory.updateIncomeCategoryById(
                incomeCategoryId.toString(),
                (binding.categoryGridView.adapter as Adapters.CategoryIconGridAdapter)
                    .selectedIconResource,
                binding.labelEditText.text.toString(),
            )
            Navigation.findNavController(it).navigateUp()
        }

        binding.deleteButton.setOnClickListener{
            IncomeCategory.deleteIncomeCategoryById(incomeCategoryId.toString())
            Navigation.findNavController(it).navigateUp()
        }

        // Actions
        IncomeCategory.getIncomeCategoryById(incomeCategoryId.toString())
            .addOnSuccessListener { document ->
                if (document != null) {
                    var incomeCategory = document.toObject(IncomeCategory::class.java)!!
                    if (incomeCategory != null) {
                        binding.categoryGridView.deferNotifyDataSetChanged()
                        val position = (binding.categoryGridView.adapter as Adapters.CategoryIconGridAdapter)
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
    }

}