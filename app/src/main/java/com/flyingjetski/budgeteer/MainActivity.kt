package com.flyingjetski.budgeteer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.flyingjetski.budgeteer.databinding.ActivityMainBinding
import com.flyingjetski.budgeteer.ui.auth.LoginFragmentDirections
import com.flyingjetski.budgeteer.ui.main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navView: BottomNavigationView
    private var previousItem: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        navView = binding.navView
        previousItem = navView.selectedItemId
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setOnNavigationItemSelectedListener { item ->
            previousItem = navView.selectedItemId
            when(item.itemId) {
                R.id.homeFragment -> {
                    when(navView.selectedItemId) {
                        R.id.sourcesFragment -> {
                            navController.navigate(SourcesFragmentDirections.actionSourcesFragmentToHomeFragment())
                        }
                        R.id.statisticsFragment -> {
                            navController.navigate(StatisticsFragmentDirections.actionStatisticsFragmentToHomeFragment())
                        }
                        R.id.moreFragment -> {
                            navController.navigate(MoreFragmentDirections.actionMoreFragmentToHomeFragment())
                        }
                        else -> false
                    }
                    true
                }
                R.id.sourcesFragment -> {
                    when(navView.selectedItemId) {
                        R.id.homeFragment -> {
                            navController.navigate(HomeFragmentDirections.actionHomeFragmentToSourcesFragment())
                        }
                        R.id.statisticsFragment -> {
                            navController.navigate(StatisticsFragmentDirections.actionStatisticsFragmentToSourcesFragment())
                        }
                        R.id.moreFragment -> {
                            navController.navigate(MoreFragmentDirections.actionMoreFragmentToSourcesFragment())
                        }
                        else -> false
                    }
                    true
                }
                R.id.addActivity -> {
                    startActivity(Intent(this, AddActivity::class.java))
                    true
                }
                R.id.statisticsFragment -> {
                    when(navView.selectedItemId) {
                        R.id.homeFragment -> {
                            navController.navigate(HomeFragmentDirections.actionHomeFragmentToStatisticsFragment())
                        }
                        R.id.sourcesFragment -> {
                            navController.navigate(SourcesFragmentDirections.actionSourcesFragmentToStatisticsFragment())
                        }
                        R.id.moreFragment -> {
                            navController.navigate(MoreFragmentDirections.actionMoreFragmentToStatisticsFragment())
                        }
                        else -> false
                    }
                    true
                }
                R.id.moreFragment -> {
                    when(navView.selectedItemId) {
                        R.id.homeFragment -> {
                            navController.navigate(HomeFragmentDirections.actionHomeFragmentToMoreFragment())
                        }
                        R.id.sourcesFragment -> {
                            navController.navigate(SourcesFragmentDirections.actionSourcesFragmentToMoreFragment())
                        }
                        R.id.statisticsFragment -> {
                            navController.navigate(StatisticsFragmentDirections.actionStatisticsFragmentToMoreFragment())
                        }
                        else -> false
                    }
                    true
                }
                else -> false
            }
        }
    }

    // [START on_start_check_user]
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = AuthActivity().auth.currentUser
        if(currentUser == null){
            startActivity(Intent(this, AuthActivity::class.java))
        }
    }
    // [END on_start_check_user]


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus) {
            navView.selectedItemId = previousItem!!
        }
    }
}