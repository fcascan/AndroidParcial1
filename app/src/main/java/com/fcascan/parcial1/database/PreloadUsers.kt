package com.fcascan.parcial1.database

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.fcascan.parcial1.R
import com.fcascan.parcial1.entities.User
import com.fcascan.parcial1.enums.getPermissionsFromString
import com.fcascan.parcial1.helpers.JsonHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException

class PreloadUsers(private val context: Context) : RoomDatabase.Callback() {
    private val USERS_JSON = "users.json"

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("PreloadUsers", "Pre-populating Users database...")
            fillWithUsers(context)
        }
    }

    private fun fillWithUsers(context: Context) {
        val appDB : AppDatabase? = AppDatabase.getInstance(context)
        val userDao = appDB?.userDao()
        try {
            val users = JsonHelper.loadJSONFromAsset(context,  R.raw.users)
            for (i in 0 until users.length()) {
                val item = users.getJSONObject(i)
                val user = User(
                    item.getInt("id"),
                    getPermissionsFromString(item.getString("permissions")),
                    item.getString("avatarUrl"),
                    item.getString("name"),
                    item.getString("lastName"),
                    item.getString("email"),
                    item.getString("password")
                )
                userDao?.insertUser(user)
            }
        } catch (e: JSONException) {
            Log.e("StartingUsers: fillWithStartingUsers(): ", e.toString())
        }
    }

}