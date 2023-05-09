package com.fcascan.parcial1.dao

import androidx.room.*
import com.fcascan.parcial1.entities.UserCategories

@Dao
interface UserCategoriesDao {
    @Query("SELECT * FROM user_categories ORDER BY id")
    fun getAllUserCategories(): MutableList<UserCategories?>?

    @Query("SELECT * FROM user_categories WHERE user_id = :user_id")
    fun getUserCategoriesByUserId(user_id: Int): List<UserCategories?>?

    @Query("SELECT * FROM user_categories WHERE category_id = :paramCategoryId")
    fun getUserCategoryByCategoryId(paramCategoryId: Int): UserCategories?

    @Query("SELECT * FROM user_categories WHERE user_id = :user_id AND category_id = :paramCategoryId")
    fun getUserCategoryByUserIdAndCategoryId(user_id: Int, paramCategoryId: Int): UserCategories?

    @Insert
    fun insertUserCategories(userCategories: UserCategories) : Long

    @Update
    fun updateUserCategories(userCategories: UserCategories) : Int

    @Delete
    fun deleteUserCategories(userCategories: UserCategories) : Int
}