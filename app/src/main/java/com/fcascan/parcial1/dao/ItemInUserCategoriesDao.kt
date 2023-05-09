package com.fcascan.parcial1.dao

import androidx.room.*
import com.fcascan.parcial1.entities.ItemInUserCategories
import com.fcascan.parcial1.entities.UserCategories

@Dao
interface ItemInUserCategoriesDao {
    @Query("SELECT * FROM items_in_user_categories")
    fun getAllItemInUserCategories(): MutableList<ItemInUserCategories?>?

    @Query("SELECT * FROM items_in_user_categories WHERE user_with_category_id = :user_with_category_id")
    fun getAllItemsWithUserCategoryId(user_with_category_id: Int): MutableList<ItemInUserCategories?>?

//    @Query("SELECT * FROM items_in_user_categories WHERE user_categories_id = :user_categories_id")
//    fun getAllItemsWithUserAndCategoryByUserId(user_categories_id: Long): MutableList<ItemInUserCategories?>?

//    @Query(
//        "SELECT * FROM items_in_user_categories " +
//                "WHERE user_id = :user_id AND category_id = :category_id"
//    )
//    fun getAllItemsWithUserAndCategoryByUserIdAndCategoryId(
//        user_id: Long,
//        category_id: Long
//    ): MutableList<ItemInUserCategories?>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItemInUserCategories(itemInUserCategories: ItemInUserCategories?) : Long

    @Update
    fun updateItemInUserCategories(itemInUserCategories: ItemInUserCategories?) : Int

    @Delete
    fun deleteItemInUserCategories(itemInUserCategories: ItemInUserCategories?) : Int
}