package com.flyingjetski.budgeteer.ui.add

import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.flyingjetski.budgeteer.*
import com.flyingjetski.budgeteer.databinding.FragmentAddExpenseBinding
import com.flyingjetski.budgeteer.models.*
import com.flyingjetski.budgeteer.models.enums.Currency
import com.flyingjetski.budgeteer.models.enums.Feedback
import java.util.*
import kotlin.collections.ArrayList


class AddExpenseFragment : Fragment() {

    private lateinit var binding: FragmentAddExpenseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_expense, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        // Instantiation
        val activity = requireActivity()
        val calendar = Calendar.getInstance()
        val calendarListener =
            OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = monthOfYear
                calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                binding.dateEditText.setText(Common.dateToString(calendar.time))
            }

        // Populate View
        ExpenseCategory.getExpenseCategory(object: Callback {
            override fun onCallback(value: Any) {
                binding.categoryGridView.adapter = Adapters.CategoryGridAdapter(
                    activity,
                    value as ArrayList<Category>,
                )
            }
        })

        Source.getSource(object: Callback {
            override fun onCallback(value: Any) {
                binding.sourceGridView.adapter = Adapters.SourceGridAdapter(
                    activity,
                    value as ArrayList<Source>,
                )
            }
        })

        // Set Listener
        binding.dateEditText.setOnClickListener{
            DatePickerDialog(
                this.requireContext(),
                calendarListener,
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            ).show()
        }

        binding.micImageView.setOnClickListener{ view ->
            getSpeechInput(view)
        }

        binding.addButton.setOnClickListener {
            Expense.insertExpense(
                Expense(
                    AuthActivity().auth.uid.toString(),
                    calendar.time,
                    (binding.sourceGridView.adapter as Adapters.SourceGridAdapter).selectedSourceId.toString(),
                    null,
                    (binding.categoryGridView.adapter as Adapters.CategoryGridAdapter).selectedCategoryId.toString(),
                    null,
                    binding.labelEditText.text.toString(),
                    binding.amountEditText.text.toString().toDouble(),
                    binding.detailsEditText.text.toString(),
                    Feedback.NEUTRAL,
                )
            )
            requireActivity().finish()
        }

        // Actions
        val today = Calendar.getInstance()
        calendar.set(
            today.time.year + 1900,
            today.time.month,
            today.time.date,
        )
        binding.dateEditText.setText(Common.dateToString(today.time))
    }

    fun getSpeechInput(view: View) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        startActivityForResult(intent, 69)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            69 -> {
                if (resultCode == RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    binding.labelEditText.setText(result?.get(0).toString())
                }
            }
        }
    }
}