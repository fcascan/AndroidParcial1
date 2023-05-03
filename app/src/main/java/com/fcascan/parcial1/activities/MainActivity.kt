package com.fcascan.parcial1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.fcascan.parcial1.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment : NavHostFragment
    private lateinit var bottomNavView : BottomNavigationView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    var paramUserMail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        paramUserMail = intent.getStringExtra("paramUserMail")
        Log.d("MainActivity", "paramUserMail: $paramUserMail")

        setContentView(R.layout.activity_main)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment
        bottomNavView = findViewById(R.id.bottom_bar)
        NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)

        toolbar = findViewById(R.id.main_activity_toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = paramUserMail
    }

    override fun onStart() {
        super.onStart()

        toolbar.setNavigationOnClickListener() {
//            findNavController(R.id.nav_host_main).navigate(R.id.profileFragment)

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        bottomNavView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.dashboardIcon -> {
//                    navHostFragment.navController.navigate(R.id.fragmentDashboard)
                    findNavController(R.id.nav_host_main).navigate(R.id.dashboardFragment)
                    true
                }
                R.id.toolsIcon -> {
//                    navHostFragment.navController.navigate(R.id.fragmentTools)
                    true
                }
                R.id.settingsIcon -> {
//                    navHostFragment.navController.navigate(R.id.fragmentSettings)
                    findNavController(R.id.nav_host_main).navigate(R.id.profileFragment)
                    true
                }
                else -> false
            }
        }
    }
}