package com.flyingjetski.budgeteer

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.flyingjetski.budgeteer.databinding.ActivityMainBinding
import com.flyingjetski.budgeteer.ui.main.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navView: BottomNavigationView
    private lateinit var navController: NavController
    var db = Firebase.firestore

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db.firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
            .build()

        db.clearPersistence()

        if (Common.expenseCategoryIcons.size == 0) {
            for (number in 1..Common.expenseCategoryIconCount) {
                Common.expenseCategoryIcons.add(
                    resources
                        .getIdentifier(
                            "expense_category_${"%03d".format(number)}",
                            "drawable",
                            packageName
                        )
                )
            }
        }

        if (Common.incomeCategoryIcons.size == 0) {
            for (number in 1..Common.incomeCategoryIconCount) {
                Common.incomeCategoryIcons.add(
                    resources
                        .getIdentifier(
                            "income_category_${"%03d".format(number)}",
                            "drawable",
                            packageName
                        )
                )
            }
        }

        if (Common.sourceIcons.size == 0) {
            for (number in 1..Common.sourceIconCount) {
                Common.sourceIcons.add(
                    resources
                        .getIdentifier(
                            "source_${"%03d".format(number)}",
                            "drawable",
                            packageName
                        )
                )
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        navView = binding.navView
        previousItem = navView.selectedItemId
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setOnNavigationItemSelectedListener { item ->
            previousItem = navView.selectedItemId
            when(item.itemId) {
                R.id.homeFragment -> {
                    when(navView.selectedItemId) {
                        R.id.sourcesFragment -> {
                            navController.navigate(SourcesFragmentDirections.actionSourcesFragmentToHomeFragment())
                        }
                        R.id.viewFragment -> {
                            navController.navigate(ViewFragmentDirections.actionViewFragmentToHomeFragment())
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
                        R.id.viewFragment -> {
                            navController.navigate(ViewFragmentDirections.actionViewFragmentToSourcesFragment())
                        }
                        R.id.moreFragment -> {
                            navController.navigate(MoreFragmentDirections.actionMoreFragmentToSourcesFragment())
                        }
                        else -> false
                    }
                    true
                }
                R.id.addActivity -> {
                    val intent = Intent(this, BlankActivity::class.java)
                    intent.putExtra("Fragment", "AddExpense")
                    startActivity(intent)
                    true
                }
                R.id.viewFragment -> {
                    when(navView.selectedItemId) {
                        R.id.homeFragment -> {
                            navController.navigate(HomeFragmentDirections.actionHomeFragmentToViewFragment())
                        }
                        R.id.sourcesFragment -> {
                            navController.navigate(SourcesFragmentDirections.actionSourcesFragmentToViewFragment())
                        }
                        R.id.moreFragment -> {
                            navController.navigate(MoreFragmentDirections.actionMoreFragmentToViewFragment())
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
                        R.id.viewFragment -> {
                            navController.navigate(ViewFragmentDirections.actionViewFragmentToMoreFragment())
                        }
                        else -> false
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus) {
            if (navView.selectedItemId == R.id.addActivity) {
                onBackPressed()
            }
            if (navView.selectedItemId == R.id.sourcesFragment) {
                onBackPressed()
            }
        }
    }

    // [START on_start_check_user]
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = AuthActivity().auth.currentUser
        if(currentUser == null){
            finish()
            startActivity(Intent(this, AuthActivity::class.java))
        } else {
            Log.d("LOGON", "${AuthActivity().auth.uid}")
        }
    }
    // [END on_start_check_user]

    override fun onBackPressed() {
        when (navView.selectedItemId) {
            R.id.addActivity -> {
                navView.selectedItemId = previousItem!!
            }
            R.id.sourcesFragment -> {
                if (previousItem == R.id.addActivity) {
                    navView.selectedItemId = R.id.homeFragment
                } else {
                    navView.selectedItemId = previousItem!!
                }
                navView.selectedItemId = R.id.sourcesFragment
            }
            else -> {
                this.moveTaskToBack(true);
            }
        }
    }

    companion object {
        private var previousItem: Int? = null
    }
}