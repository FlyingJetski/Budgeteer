package com.flyingjetski.budgeteer.ui.add

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.Adapters
import com.flyingjetski.budgeteer.BlankActivity
import com.flyingjetski.budgeteer.Callback
import com.flyingjetski.budgeteer.databinding.FragmentViewExpenseBinding
import com.flyingjetski.budgeteer.models.Budget
import com.flyingjetski.budgeteer.models.Expense

class ViewExpenseFragment : Fragment() {

    lateinit var binding: FragmentViewExpenseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        Log.d("ZXC", "ZXC1")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_expense, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        // Populate View
        Log.d("ZXC", "ZXC")
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
            val intent = Intent(requireContext(), BlankActivity::class.java)
            intent.putExtra("Fragment", "EditExpense")
            intent.putExtra("Id", (binding.listView.adapter.getItem(position) as Expense).id.toString())
            startActivity(intent)
        }
    }

}