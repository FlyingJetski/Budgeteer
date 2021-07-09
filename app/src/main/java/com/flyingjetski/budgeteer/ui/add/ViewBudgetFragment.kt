package com.flyingjetski.budgeteer.ui.add

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
import com.flyingjetski.budgeteer.Callback
import com.flyingjetski.budgeteer.databinding.FragmentViewBudgetBinding
import com.flyingjetski.budgeteer.models.Budget
import com.flyingjetski.budgeteer.models.Category

class ViewBudgetFragment : Fragment() {

    lateinit var binding: FragmentViewBudgetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_budget, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        // Populate View
        Budget.getBudget(object: Callback {
            override fun onCallback(value: Any) {
                binding.listView.adapter = Adapters.BudgetListAdapter(
                    requireContext(),
                    value as ArrayList<Budget>,
                )
            }
        })

        // Set Listeners
//        binding.listView.setOnItemClickListener{adapterView, view, position, id ->
//            Navigation.findNavController(view).navigate(
//                ViewBudgetFragmentDirections
//                    .actionViewBudgetFragmentToEditBudgetFragment(
//                        (binding.listView.adapter.getItem(position) as Budget).id.toString()
//                    )
//            )
//        }
    }

}