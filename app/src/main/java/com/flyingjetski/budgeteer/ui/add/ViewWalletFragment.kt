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
import com.flyingjetski.budgeteer.databinding.FragmentViewWalletBinding
import com.flyingjetski.budgeteer.models.Category
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
        Wallet.getWallet()
            .addSnapshotListener{
                    snapshot, _ ->
                run {
                    if (snapshot != null) {
                        val wallets = ArrayList<Wallet>()
                        val documents = snapshot.documents
                        documents.forEach {
                            val wallet = it.toObject(Wallet::class.java)
                            if (wallet != null) {
                                wallet.id = it.id
                                wallets.add(wallet)
                            }
                        }
                        binding.listView.adapter = Adapters.WalletListAdapter(
                            this.requireContext(),
                            wallets,
                        )
                    }
                }
            }

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