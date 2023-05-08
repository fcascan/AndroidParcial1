package com.fcascan.parcial1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.fcascan.parcial1.R
import com.fcascan.parcial1.dao.UserDao
import com.fcascan.parcial1.database.AppDatabase
import com.fcascan.parcial1.entities.User
import com.fcascan.parcial1.enums.Permissions
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment : NavHostFragment
    private lateinit var bottomNavView : BottomNavigationView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    var paramUserMail: String? = null

    private var userDao : UserDao? = null
    private var userPermissions: Permissions? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment
        bottomNavView = findViewById(R.id.bottom_bar)
        NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)

        val appDB : AppDatabase? = AppDatabase.getInstance(this)
        userDao = appDB?.userDao()

        showUserInActionBar()
        getUserPermissions()
        setupItemsFromToolbar()
    }

    override fun onStart() {
        super.onStart()

        onAdminRedirect()

        toolbar.setNavigationOnClickListener {
            exitMainActivity()
        }

        bottomNavView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.dashboardIcon -> {
                    findNavController(R.id.nav_host_main).navigate(R.id.dashboardFragment)
                    true
                }
                R.id.toolsIcon -> {
                    findNavController(R.id.nav_host_main).navigate(R.id.toolsFragment)
                    true
                }
                R.id.settingsIcon -> {
                    findNavController(R.id.nav_host_main).navigate(R.id.profileFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun showUserInActionBar() {
        toolbar = findViewById(R.id.main_activity_toolbar)
        setSupportActionBar(toolbar)
        paramUserMail = intent.getStringExtra("paramUserMail")
        Log.d("MainActivity", "paramUserMail: $paramUserMail")
        paramUserMail?.let { updateToolbarTitle(it) }
    }

    private fun getUserPermissions(){
        val user: User? = userDao?.getUserByEmail(paramUserMail.toString())
        userPermissions = user?.permissions
        Log.d("MainActivity", "userPermissions: $userPermissions")
    }

    private fun setupItemsFromToolbar() {
        val toolsIcon = bottomNavView.menu.findItem(R.id.toolsIcon)
        val dashboardIcon = bottomNavView.menu.findItem(R.id.dashboardIcon)
        val settingsIcon = bottomNavView.menu.findItem(R.id.settingsIcon)
        if (userPermissions == Permissions.ADMIN) {
            dashboardIcon.isVisible = false
            toolsIcon.isVisible = true
            settingsIcon.isVisible = true
        } else {
            dashboardIcon.isVisible = true
            toolsIcon.isVisible = false
            settingsIcon.isVisible = true
        }
    }

    private fun onAdminRedirect() {
        if (userPermissions == Permissions.ADMIN) {
            findNavController(R.id.nav_host_main).navigate(R.id.toolsFragment)
        }
    }

    fun updateToolbarTitle(title: String) {
        toolbar.title = title
        Log.d("MainActivity", "New toolbar title: $title")
    }

    fun exitMainActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}