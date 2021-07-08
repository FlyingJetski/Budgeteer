package com.flyingjetski.budgeteer.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.Adapters
import com.flyingjetski.budgeteer.Callback
import com.flyingjetski.budgeteer.databinding.FragmentViewExpenseBinding
import com.flyingjetski.budgeteer.models.Expense

class ViewExpenseFragment : Fragment() {

    lateinit var binding: FragmentViewExpenseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_expense, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        // Populate View
        Expense.getAllExpense(object: Callback {
            override fun onCallback(value: Any) {
                binding.listView.adapter = Adapters.ExpenseListAdapter(
                    requireContext(),
                    value as ArrayList<Expense>,
                )
            }
        })

        // Set Listeners
        binding.listView.setOnItemClickListener{adapterView, view, position, id ->
            Navigation.findNavController(view).navigate(
                ViewExpenseFragmentDirections
                    .actionViewExpenseFragmentToEditExpenseFragment(
                        (binding.listView.adapter.getItem(position) as Expense).id.toString()
                    )
            )
        }
    }

}