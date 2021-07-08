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
import com.flyingjetski.budgeteer.databinding.FragmentViewWalletBinding
import com.flyingjetski.budgeteer.models.Category
import com.flyingjetski.budgeteer.models.ExpenseCategory
import com.flyingjetski.budgeteer.models.Wallet

class ViewWalletFragment : Fragment() {

    lateinit var binding: FragmentViewWalletBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_wallet, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        // Populate View
        Wallet.getWallet(object: Callback {
            override fun onCallback(value: Any) {
                binding.listView.adapter = Adapters.WalletListAdapter(
                    requireContext(),
                    value as ArrayList<Wallet>,
                )
            }
        })

        // Set Listeners
        binding.listView.setOnItemClickListener{adapterView, view, position, id ->
            Navigation.findNavController(view).navigate(
                ViewWalletFragmentDirections
                    .actionViewWalletFragmentToEditWalletFragment(
                        (binding.listView.adapter.getItem(position) as Wallet).id.toString()
                    )
            )
        }
    }

}