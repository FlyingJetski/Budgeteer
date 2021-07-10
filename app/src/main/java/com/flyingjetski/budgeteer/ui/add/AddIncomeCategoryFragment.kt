package com.flyingjetski.budgeteer.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.flyingjetski.budgeteer.Adapters
import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.Common
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentAddIncomeCategoryBinding
import com.flyingjetski.budgeteer.models.IncomeCategory
import java.lang.reflect.Field

class AddIncomeCategoryFragment : Fragment() {

    private lateinit var binding: FragmentAddIncomeCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_income_category, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        // Instantiation
        val activity = requireActivity()

        // Populate View
        binding.categoryGridView.adapter =
            Adapters.IconGridAdapter(this.requireContext(), Common.incomeCategoryIcons)

        // Set Listener
        binding.addButton.setOnClickListener {
            IncomeCategory.insertIncomeCategory(
                IncomeCategory(
                    AuthActivity().auth.uid.toString(),
                    (binding.categoryGridView.adapter as
                            Adapters.IconGridAdapter).selectedIconResource,
                    binding.labelEditText.text.toString()
                )
            )
            activity.onBackPressed()
        }
    }

}