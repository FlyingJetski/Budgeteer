package com.flyingjetski.budgeteer.ui.add

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
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
import com.flyingjetski.budgeteer.Common
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentAddSavingBinding
import com.flyingjetski.budgeteer.models.AutoSave
import com.flyingjetski.budgeteer.models.Saving
import com.flyingjetski.budgeteer.models.enums.Currency
import java.lang.reflect.Field
import java.util.*
import kotlin.collections.ArrayList

class AddSavingFragment : Fragment() {

    private lateinit var binding: FragmentAddSavingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_saving, container, false)
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

        val calendar = Calendar.getInstance()
        val calendarListener =
            OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = monthOfYear
                calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                binding.deadlineDateEditText.setText(Common.dateToString(calendar.time))
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
        binding.deadlineDateEditText.setOnClickListener{
            DatePickerDialog(
                this.requireContext(), calendarListener, calendar[Calendar.YEAR], calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            ).show()
        }

        binding.addButton.setOnClickListener {
            Saving.insertSaving(
                Saving(
                    AuthActivity().auth.uid.toString(),
                    true,
                    (binding.categoryGridView.adapter as
                            Adapters.IconGridAdapter).selectedIconResource,
                    binding.labelEditText.text.toString(),
                    binding.currencySpinner.selectedItem as Currency,
                    binding.targetEditText.text.toString().toDouble(),
                    calendar.time,
                    AutoSave(),
                )
            )
            Navigation.findNavController(it).navigateUp()
        }
    }

}