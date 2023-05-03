package com.fcascan.parcial1.dao

import androidx.room.*
import com.fcascan.parcial1.entities.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY name")
    fun getAllCategories(): MutableList<Category?>?

    @Query("SELECT * FROM categories WHERE id = :id")
    fun getCategoryById(id: Int): Category?

    @Query("SELECT * FROM categories WHERE name = :name")
    fun getCategoryByName(name: String): Category?

    @Insert
    fun insertCategory(category: Category) : Long

    @Update
    fun updateCategory(category: Category) : Int

    @Delete
    fun deleteCategory(category: Category) : Int
}