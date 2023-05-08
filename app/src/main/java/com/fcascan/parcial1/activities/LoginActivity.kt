package com.fcascan.parcial1.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fcascan.parcial1.R
import com.fcascan.parcial1.dao.*
import com.fcascan.parcial1.database.*
import com.fcascan.parcial1.entities.User
import com.fcascan.parcial1.enums.Permissions

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initDatabase()
//        initDatabase(hardcodeAdmin = true)
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    private fun initDatabase(hardcodeAdmin : Boolean = false){
        val appDB : AppDatabase? = AppDatabase.getInstance(baseContext)

        val userDao : UserDao? = appDB?.userDao()
        if (userDao != null && hardcodeAdmin) {
            userDao.insertUser(
                User(0, "Admin", "Admin", "admin", "admin", Permissions.ADMIN, "url"))
        }
        userDao?.getAllUsers()

        val categoryDao : CategoryDao? = appDB?.categoryDao()
        categoryDao?.getAllCategories()

        val itemDao : ItemDao? = appDB?.itemDao()
        itemDao?.getAllItems()

        val userCategoriesDao : UserCategoriesDao? = appDB?.userCategoriesDao()
        userCategoriesDao?.getAllUserCategories()

        val itemInUserCategoriesDao : ItemInUserCategoriesDao? = appDB?.itemInUserCategoriesDao()
        itemInUserCategoriesDao?.getAllItemInUserCategories()
    }
}