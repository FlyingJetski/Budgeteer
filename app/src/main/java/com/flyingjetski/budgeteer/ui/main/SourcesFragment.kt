package com.flyingjetski.budgeteer.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.flyingjetski.budgeteer.*
import com.flyingjetski.budgeteer.databinding.FragmentSourcesBinding
import com.flyingjetski.budgeteer.models.Budget
import com.flyingjetski.budgeteer.models.Saving
import com.flyingjetski.budgeteer.models.Wallet

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
                val wallets = value as ArrayList<Wallet>
                val inflater = LayoutInflater.from(requireContext())
                wallets.forEach{ wallet ->
                    val rowView = inflater.inflate(R.layout.list_wallet, binding.walletLinearLayout, false)

                    val idTextView = rowView.findViewById(R.id.idTextView) as TextView
                    val iconImageView = rowView.findViewById(R.id.iconImageView) as ImageView
                    val labelTextView = rowView.findViewById(R.id.labelTextView) as TextView
                    val amountTextView = rowView.findViewById(R.id.amountTextView) as TextView
                    val currencyTextView = rowView.findViewById(R.id.currencyTextView) as TextView

                    idTextView.text = wallet.id
                    iconImageView.setImageResource(wallet.icon)
                    labelTextView.text = wallet.label
                    if (wallet.amount != null) {
                        amountTextView.text = Common.roundDouble(wallet.amount!!).toString()
                    } else {
                        amountTextView.text = "0"
                    }
                    currencyTextView.text = wallet.currency.toString()

                    rowView.setOnClickListener{ _ ->
                        val intent = Intent(requireContext(), BlankActivity::class.java)
                        intent.putExtra("Fragment", "EditWallet")
                        intent.putExtra("Id", idTextView.text.toString())
                        startActivity(intent)
                    }

                    binding.walletLinearLayout.addView(rowView)
                }
            }
        })

        Saving.getSaving(object: Callback {
            override fun onCallback(value: Any) {
                val savings = value as ArrayList<Saving>
                val inflater = LayoutInflater.from(requireContext())
                savings.forEach{ saving ->
                    val rowView = inflater.inflate(R.layout.list_saving, binding.savingLinearLayout, false)

                    val idTextView = rowView.findViewById(R.id.idTextView) as TextView
                    val iconImageView = rowView.findViewById(R.id.iconImageView) as ImageView
                    val labelTextView = rowView.findViewById(R.id.labelTextView) as TextView
                    val amountTextView = rowView.findViewById(R.id.amountTextView) as TextView
                    val targetTextView = rowView.findViewById(R.id.targetTextView) as TextView
                    val currencyTextView = rowView.findViewById(R.id.currencyTextView) as TextView
                    val deadlineTextView = rowView.findViewById(R.id.deadlineTextView) as TextView

                    idTextView.text = saving.id
                    iconImageView.setImageResource(saving.icon)
                    labelTextView.text = saving.label
                    if (saving.amount != null) {
                        amountTextView.text = Common.roundDouble(saving.amount!!).toString()
                    } else {
                        amountTextView.text = "0"
                    }
                    targetTextView.text = Common.roundDouble(saving.target).toString()
                    currencyTextView.text = saving.currency.toString()
                    deadlineTextView.text = Common.dateToString(saving.deadline)

                    rowView.setOnClickListener{ _ ->
                        val intent = Intent(requireContext(), BlankActivity::class.java)
                        intent.putExtra("Fragment", "EditSaving")
                        intent.putExtra("Id", idTextView.text.toString())
                        startActivity(intent)
                    }

                    binding.savingLinearLayout.addView(rowView)
                }
            }
        })

        Budget.getBudget(object: Callback {
            override fun onCallback(value: Any) {
                val budgets = value as ArrayList<Budget>
                val inflater = LayoutInflater.from(requireContext())
                budgets.forEach{ budget ->
                    val rowView = inflater.inflate(R.layout.list_budget, binding.budgetLinearLayout, false)

                    val idTextView = rowView.findViewById(R.id.idTextView) as TextView
                    val iconImageView = rowView.findViewById(R.id.iconImageView) as ImageView
                    val labelTextView = rowView.findViewById(R.id.labelTextView) as TextView
                    val amountSpentTextView = rowView.findViewById(R.id.amountSpentTextView) as TextView
                    val amountTextView = rowView.findViewById(R.id.amountTextView) as TextView
                    val currencyTextView = rowView.findViewById(R.id.currencyTextView) as TextView
                    val startDateTextView = rowView.findViewById(R.id.startDateTextView) as TextView
                    val endDateTextView = rowView.findViewById(R.id.dateTextView) as TextView

                    idTextView.text = budget.id
                    iconImageView.setImageResource(budget.icon)
                    labelTextView.text = budget.label
                    amountSpentTextView.text = Common.roundDouble(budget.amountSpent).toString()
                    amountTextView.text = Common.roundDouble(budget.amount).toString()
                    currencyTextView.text = budget.currency.toString()
                    startDateTextView.text = Common.dateToString(budget.startDate)
                    endDateTextView.text = Common.dateToString(budget.endDate)

                    rowView.setOnClickListener{ _ ->
                        val intent = Intent(requireContext(), BlankActivity::class.java)
                        intent.putExtra("Fragment", "EditBudget")
                        intent.putExtra("Id", idTextView.text.toString())
                        startActivity(intent)
                    }

                    binding.budgetLinearLayout.addView(rowView)
                }
            }
        })

        // Set Listeners
        binding.addWalletButton.setOnClickListener { view ->
            val intent = Intent(requireContext(), BlankActivity::class.java)
            intent.putExtra("Fragment", "AddWallet")
            startActivity(intent)
        }

        binding.addSavingButton.setOnClickListener { view ->
            val intent = Intent(requireContext(), BlankActivity::class.java)
            intent.putExtra("Fragment", "AddSaving")
            startActivity(intent)
        }

        binding.addBudgetButton.setOnClickListener { view ->
            val intent = Intent(requireContext(), BlankActivity::class.java)
            intent.putExtra("Fragment", "AddBudget")
            startActivity(intent)
        }
    }
}