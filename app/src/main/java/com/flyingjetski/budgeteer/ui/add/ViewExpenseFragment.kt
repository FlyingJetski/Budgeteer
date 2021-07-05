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
import com.flyingjetski.budgeteer.databinding.FragmentViewExpenseBinding
import com.flyingjetski.budgeteer.models.Category
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
        Expense.getExpense()
            .addSnapshotListener{
                    snapshot, _ ->
                run {
                    if (snapshot != null) {
                        val expenses = ArrayList<Expense>()
                        val documents = snapshot.documents
                        documents.forEach {
                            val expense = it.toObject(Expense::class.java)
                            if (expense != null) {
                                expense.id = it.id
                                expenses.add(expense)
                            }
                        }
                        binding.listView.adapter = Adapters.ExpenseListAdapter(
                            this.requireContext(),
                            expenses,
                        )
                    }
                }
            }

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