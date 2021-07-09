package com.flyingjetski.budgeteer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.flyingjetski.budgeteer.databinding.ActivityBlankBinding
import com.flyingjetski.budgeteer.ui.add.*


class BlankActivity: AppCompatActivity() {

    private lateinit var binding: ActivityBlankBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_blank)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val id = intent.getStringExtra("Id").toString()
        when (intent.getStringExtra("Fragment").toString()) {
            "AddBudget" -> {
                fragmentTransaction.replace(binding.content.id, AddBudgetFragment())
            }
            "EditBudget" -> {
                val fragment = EditBudgetFragment()
                val bundle = Bundle()
                bundle.putString("Id", id)
                fragment.arguments = bundle
                fragmentTransaction.replace(binding.content.id, fragment)
            }
            "AddSaving" -> {
                fragmentTransaction.replace(binding.content.id, AddSavingFragment())
            }
            "EditSaving" -> {
                val fragment = EditSavingFragment()
                val bundle = Bundle()
                bundle.putString("Id", id)
                fragment.arguments = bundle
                fragmentTransaction.replace(binding.content.id, fragment)
            }
            "AddWallet" -> {
                fragmentTransaction.replace(binding.content.id, AddWalletFragment())
            }
            "EditWallet" -> {
                val fragment = EditWalletFragment()
                val bundle = Bundle()
                bundle.putString("Id", id)
                fragment.arguments = bundle
                fragmentTransaction.replace(binding.content.id, fragment)
            }
            "AddExpense" -> {
                fragmentTransaction.replace(binding.content.id, AddExpenseFragment())
            }
            "EditExpense" -> {
                val fragment = EditExpenseFragment()
                val bundle = Bundle()
                bundle.putString("Id", id)
                fragment.arguments = bundle
                fragmentTransaction.replace(binding.content.id, fragment)
            }
            "ViewExpense" -> {
                fragmentTransaction.replace(binding.content.id, ViewExpenseFragment())
            }
            "AddExpenseCategory" -> {
                fragmentTransaction.replace(binding.content.id, AddExpenseCategoryFragment())
            }
            "EditExpenseCategory" -> {
                val fragment = EditExpenseCategoryFragment()
                val bundle = Bundle()
                bundle.putString("Id", id)
                fragment.arguments = bundle
                fragmentTransaction.replace(binding.content.id, fragment)
            }
            "ViewExpenseCategory" -> {
                fragmentTransaction.replace(binding.content.id, ViewExpenseCategoryFragment())
            }
            "AddIncome" -> {
                fragmentTransaction.replace(binding.content.id, AddIncomeFragment())
            }
            "EditIncome" -> {
                val fragment = EditIncomeFragment()
                val bundle = Bundle()
                bundle.putString("Id", id)
                fragment.arguments = bundle
                fragmentTransaction.replace(binding.content.id, fragment)
            }
            "ViewIncome" -> {
                fragmentTransaction.replace(binding.content.id, ViewIncomeFragment())
            }
            "AddIncomeCategory" -> {
                fragmentTransaction.replace(binding.content.id, AddIncomeCategoryFragment())
            }
            "EditIncomeCategory" -> {
                val fragment = EditIncomeCategoryFragment()
                val bundle = Bundle()
                bundle.putString("Id", id)
                fragment.arguments = bundle
                fragmentTransaction.replace(binding.content.id, fragment)
            }
            "ViewIncomeCategory" -> {
                fragmentTransaction.replace(binding.content.id, ViewIncomeCategoryFragment())
            }
        }
        fragmentTransaction
//            .disallowAddToBackStack()
            .commit()
    }

}