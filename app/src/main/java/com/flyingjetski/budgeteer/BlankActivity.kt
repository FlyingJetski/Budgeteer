package com.flyingjetski.budgeteer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
                fragmentTransaction.replace(binding.fragmentContainer.id, AddBudgetFragment())
            }
            "EditBudget" -> {
                val fragment = EditBudgetFragment()
                val bundle = Bundle()
                bundle.putString("Id", id)
                fragment.arguments = bundle
                fragmentTransaction.replace(binding.fragmentContainer.id, fragment)
            }
            "AddSaving" -> {
                fragmentTransaction.replace(binding.fragmentContainer.id, AddSavingFragment())
            }
            "EditSaving" -> {
                val fragment = EditSavingFragment()
                val bundle = Bundle()
                bundle.putString("Id", id)
                fragment.arguments = bundle
                fragmentTransaction.replace(binding.fragmentContainer.id, fragment)
            }
            "AddWallet" -> {
                fragmentTransaction.replace(binding.fragmentContainer.id, AddWalletFragment())
            }
            "EditWallet" -> {
                val fragment = EditWalletFragment()
                val bundle = Bundle()
                bundle.putString("Id", id)
                fragment.arguments = bundle
                fragmentTransaction.replace(binding.fragmentContainer.id, fragment)
            }
            "AddExpense" -> {
                fragmentTransaction.replace(binding.fragmentContainer.id, AddExpenseFragment())
            }
            "EditExpense" -> {
                val fragment = EditExpenseFragment()
                val bundle = Bundle()
                bundle.putString("Id", id)
                fragment.arguments = bundle
                fragmentTransaction.replace(binding.fragmentContainer.id, fragment)
            }
            "ViewExpense" -> {
                fragmentTransaction.replace(binding.fragmentContainer.id, ViewExpenseFragment())
            }
            "AddExpenseCategory" -> {
                fragmentTransaction.replace(binding.fragmentContainer.id, AddExpenseCategoryFragment())
            }
            "EditExpenseCategory" -> {
                val fragment = EditExpenseCategoryFragment()
                val bundle = Bundle()
                bundle.putString("Id", id)
                fragment.arguments = bundle
                fragmentTransaction.replace(binding.fragmentContainer.id, fragment)
            }
            "ViewExpenseCategory" -> {
                fragmentTransaction.replace(binding.fragmentContainer.id, ViewExpenseCategoryFragment())
            }
            "AddIncome" -> {
                fragmentTransaction.replace(binding.fragmentContainer.id, AddIncomeFragment())
            }
            "EditIncome" -> {
                val fragment = EditIncomeFragment()
                val bundle = Bundle()
                bundle.putString("Id", id)
                fragment.arguments = bundle
                fragmentTransaction.replace(binding.fragmentContainer.id, fragment)
            }
            "ViewIncome" -> {
                fragmentTransaction.replace(binding.fragmentContainer.id, ViewIncomeFragment())
            }
            "AddIncomeCategory" -> {
                fragmentTransaction.replace(binding.fragmentContainer.id, AddIncomeCategoryFragment())
            }
            "EditIncomeCategory" -> {
                val fragment = EditIncomeCategoryFragment()
                val bundle = Bundle()
                bundle.putString("Id", id)
                fragment.arguments = bundle
                fragmentTransaction.replace(binding.fragmentContainer.id, fragment)
            }
            "ViewIncomeCategory" -> {
                fragmentTransaction.replace(binding.fragmentContainer.id, ViewIncomeCategoryFragment())
            }
        }
        fragmentTransaction
            .addToBackStack(null)
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    fun navigateToFragment(fragment: Fragment, id: String?) {
        if (id != null) {
            val bundle = Bundle()
            bundle.putString("Id", id)
            fragment.arguments = bundle
        }

        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .addToBackStack(null)
            .commit()
    }

}