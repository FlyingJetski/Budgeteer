package com.flyingjetski.budgeteer.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.flyingjetski.budgeteer.Adapters
import com.flyingjetski.budgeteer.BlankActivity
import com.flyingjetski.budgeteer.Callback
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentSourcesBinding
import com.flyingjetski.budgeteer.databinding.FragmentViewBinding
import com.flyingjetski.budgeteer.models.Budget
import com.flyingjetski.budgeteer.models.Saving
import com.flyingjetski.budgeteer.models.Wallet
import com.flyingjetski.budgeteer.ui.add.ViewExpenseFragment

class SourcesFragment : Fragment() {

    private lateinit var binding: FragmentSourcesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sources, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        // Populate View
        Wallet.getWallet(object: Callback {
            override fun onCallback(value: Any) {
                binding.walletListView.adapter = Adapters.WalletListAdapter(
                    requireContext(),
                    value as ArrayList<Wallet>,
                )
            }
        })

        Budget.getBudget(object: Callback {
            override fun onCallback(value: Any) {
                binding.budgetListView.adapter = Adapters.BudgetListAdapter(
                    requireContext(),
                    value as ArrayList<Budget>,
                )
            }
        })

        Saving.getSaving(object: Callback {
            override fun onCallback(value: Any) {
                binding.savingListView.adapter = Adapters.SavingListAdapter(
                    requireContext(),
                    value as ArrayList<Saving>,
                )
            }
        })

        // Set Listeners
        binding.walletListView.setOnItemClickListener{adapterView, view, position, id ->
            val intent = Intent(requireContext(), BlankActivity::class.java)
            intent.putExtra("Fragment", "EditWallet")
            intent.putExtra("Id", (binding.walletListView.adapter.getItem(position) as Wallet).id.toString())
            startActivity(intent)
        }

        binding.budgetListView.setOnItemClickListener{adapterView, view, position, id ->
            val intent = Intent(requireContext(), BlankActivity::class.java)
            intent.putExtra("Fragment", "EditBudget")
            intent.putExtra("Id", (binding.budgetListView.adapter.getItem(position) as Budget).id.toString())
            startActivity(intent)
        }

        binding.savingListView.setOnItemClickListener{adapterView, view, position, id ->
            val intent = Intent(requireContext(), BlankActivity::class.java)
            intent.putExtra("Fragment", "EditSaving")
            intent.putExtra("Id", (binding.savingListView.adapter.getItem(position) as Saving).id.toString())
            startActivity(intent)
        }
    }

}