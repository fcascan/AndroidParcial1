package com.fcascan.parcial1.dao

import androidx.room.*
import com.fcascan.parcial1.entities.UserCategories

@Dao
interface UserCategoriesDao {
    @Query("SELECT * FROM user_categories ORDER BY id")
    fun getAllUserCategories(): MutableList<UserCategories?>?

    @Query("SELECT * FROM user_categories WHERE user_id = :user_id")
    fun getUserCategoriesByUserId(user_id: Int): MutableList<UserCategories?>?

    @Query("SELECT * FROM user_categories WHERE category_id = :paramCategoryId")
    fun getUserCategoriesByCategoryId(paramCategoryId: Int): UserCategories?

    @Insert
    fun insertUserCategories(userCategories: UserCategories) : Long

    @Update
    fun updateUserCategories(userCategories: UserCategories) : Int

    @Delete
    fun deleteUserCategories(userCategories: UserCategories) : Int
}