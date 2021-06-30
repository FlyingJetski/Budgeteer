package com.flyingjetski.budgeteer

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.flyingjetski.budgeteer.databinding.ActivityAddBinding
import com.flyingjetski.budgeteer.databinding.ActivityAuthBinding
import com.flyingjetski.budgeteer.databinding.ActivityMainBinding
import com.flyingjetski.budgeteer.ui.add.AddExpenseFragment

class AddActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("UNUSED_VARIABLE")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add)
//        val navController = this.findNavController(R.id.add_navigation)
//        NavigationUI.setupActionBarWithNavController(this, navController)

        // showing the back button in action bar
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//        binding.addExpenseButton.setOnClickListener{
//            supportFragmentManager.beginTransaction().add(R.id.add_navigation, AddExpenseFragment())
//        }
    }

//    override fun onContextItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            android.R.id.home -> {
//                finish()
//                return true
//            }
//        }
//        return super.onContextItemSelected(item)
//    }

}