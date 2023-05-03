package com.fcascan.parcial1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fcascan.parcial1.dao.*
import com.fcascan.parcial1.entities.*

@Database(entities = [User::class, Category::class, Item::class, UserCategories::class, ItemInUserCategories::class],
    version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao?
    abstract fun categoryDao(): CategoryDao?
    abstract fun itemDao(): ItemDao?
    abstract fun userCategoriesDao(): UserCategoriesDao?
    abstract fun itemInUserCategoriesDao(): ItemInUserCategoriesDao?

    companion object {
        private var INSTANCE: AppDatabase? = null
        private const val DATABASE_NAME = "parcial1_db"

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                INSTANCE = buildDatabase(context)
            }
            return INSTANCE
        }

        fun buildDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DATABASE_NAME
                    )
                        .addCallback(PreloadUsers(context))
//                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}