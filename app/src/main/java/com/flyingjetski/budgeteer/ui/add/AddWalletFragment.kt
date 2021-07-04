package com.flyingjetski.budgeteer.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.flyingjetski.budgeteer.Adapters
import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentAddWalletBinding
import com.flyingjetski.budgeteer.databinding.FragmentHomeBinding
import com.flyingjetski.budgeteer.models.ExpenseCategory
import com.flyingjetski.budgeteer.models.Wallet
import com.flyingjetski.budgeteer.models.enums.Currency
import java.lang.reflect.Field

class AddWalletFragment : Fragment() {

    private lateinit var binding: FragmentAddWalletBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_wallet, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        // Instantiation
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
            Adapters.CategoryIconGridAdapter(this.requireContext(), icons)
        binding.currencySpinner.adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                Currency.values()
            )

        // Set Listeners
        binding.addButton.setOnClickListener {
            Wallet.insertWallet(
                Wallet(
                    AuthActivity().auth.uid.toString(),
                    (binding.categoryGridView.adapter as
                            Adapters.CategoryIconGridAdapter).selectedIconResource,
                    binding.labelEditText.text.toString(),
                    binding.currencySpinner.selectedItem as Currency,
                    false
                )
            )
            Navigation.findNavController(it).navigateUp()
        }
    }

}