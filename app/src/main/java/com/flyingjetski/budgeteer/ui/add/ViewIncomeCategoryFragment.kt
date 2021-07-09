package com.flyingjetski.budgeteer.ui.add

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentViewExpenseCategoryBinding
import com.flyingjetski.budgeteer.models.ExpenseCategory
import com.flyingjetski.budgeteer.Adapters
import com.flyingjetski.budgeteer.BlankActivity
import com.flyingjetski.budgeteer.Callback
import com.flyingjetski.budgeteer.databinding.FragmentViewIncomeCategoryBinding
import com.flyingjetski.budgeteer.models.Category
import com.flyingjetski.budgeteer.models.IncomeCategory

class ViewIncomeCategoryFragment : Fragment() {

    lateinit var binding: FragmentViewIncomeCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_income_category, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        // Populate View
        IncomeCategory.getIncomeCategory(object: Callback {
            override fun onCallback(value: Any) {
                binding.listView.adapter = Adapters.CategoryListAdapter(
                    requireContext(),
                    value as ArrayList<Category>,
                )
            }
        })

        // Set Listeners
        binding.listView.setOnItemClickListener{adapterView, view, position, id ->
            val intent = Intent(requireContext(), BlankActivity::class.java)
            intent.putExtra("Fragment", "EditIncomeCategory")
            intent.putExtra("Id", (binding.listView.adapter.getItem(position) as IncomeCategory).id.toString())
            startActivity(intent)
        }
    }

}