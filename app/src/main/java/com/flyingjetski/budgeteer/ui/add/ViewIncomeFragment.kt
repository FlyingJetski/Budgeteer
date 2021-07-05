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
import com.flyingjetski.budgeteer.databinding.FragmentViewIncomeBinding
import com.flyingjetski.budgeteer.models.Income

class ViewIncomeFragment : Fragment() {

    lateinit var binding: FragmentViewIncomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_income, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        // Populate View
        Income.getIncome()
            .addSnapshotListener{
                    snapshot, _ ->
                run {
                    if (snapshot != null) {
                        val incomes = ArrayList<Income>()
                        val documents = snapshot.documents
                        documents.forEach {
                            val income = it.toObject(Income::class.java)
                            if (income != null) {
                                income.id = it.id
                                incomes.add(income)
                            }
                        }
                        binding.listView.adapter = Adapters.IncomeListAdapter(
                            this.requireContext(),
                            incomes,
                        )
                    }
                }
            }

        // Set Listeners
        binding.listView.setOnItemClickListener{adapterView, view, position, id ->
            Navigation.findNavController(view).navigate(
                ViewIncomeFragmentDirections
                    .actionViewIncomeFragmentToEditIncomeFragment(
                        (binding.listView.adapter.getItem(position) as Income).id.toString()
                    )
            )
        }
    }

}