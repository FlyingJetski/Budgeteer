package com.flyingjetski.budgeteer.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.flyingjetski.budgeteer.Adapters
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentEditWalletBinding
import com.flyingjetski.budgeteer.models.Saving
import com.flyingjetski.budgeteer.models.Source
import com.flyingjetski.budgeteer.models.Wallet
import com.flyingjetski.budgeteer.models.enums.Currency
import java.lang.reflect.Field

class EditWalletFragment : Fragment() {

    private lateinit var binding: FragmentEditWalletBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_wallet, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        var walletId = arguments?.getString("walletId")
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
            Adapters.IconGridAdapter(this.requireContext(), icons)
        binding.currencySpinner.adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                Currency.values()
            )


        // Set Listeners
        binding.categoryGridView.setOnItemClickListener{ adapterView: AdapterView<*>, _, position: Int, _ ->
            (adapterView.adapter as Adapters.IconGridAdapter)
                .selectIcon(position)
        }

        binding.editButton.setOnClickListener{
            Wallet.updateWalletById(
                walletId.toString(),
                (binding.categoryGridView.adapter as Adapters.IconGridAdapter)
                    .selectedIconResource,
                binding.labelEditText.text.toString(),
                binding.currencySpinner.selectedItem as Currency,
            )
            Navigation.findNavController(it).navigateUp()
        }

        binding.deleteButton.setOnClickListener{
            Source.deleteSourceById(walletId.toString())
            Navigation.findNavController(it).navigateUp()
        }

        // Actions
        Source.getSourceById(walletId.toString())
            .addOnSuccessListener { document ->
                if (document != null) {
                    var saving = document.toObject(Saving::class.java)!!
                    if (saving != null) {
                        binding.categoryGridView.deferNotifyDataSetChanged()
                        val position = (binding.categoryGridView.adapter as Adapters.IconGridAdapter)
                            .getPositionOfResource(saving.icon)
                        binding.categoryGridView.performItemClick(
                            binding.categoryGridView,
                            position,
                            binding.categoryGridView.adapter.getItemId(position),
                        )
                        binding.labelEditText.setText(saving.label)
                        for (position in 0 until binding.currencySpinner.count) {
                            if ((binding.currencySpinner.getItemAtPosition(position) as Currency) == saving.currency) {
                                binding.currencySpinner.setSelection(position)
                                break
                            }
                        }
                    }
                }
            }
    }

}