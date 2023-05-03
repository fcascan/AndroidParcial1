package com.fcascan.parcial1.dao

import androidx.room.*
import com.fcascan.parcial1.entities.Item

@Dao
interface ItemDao {
    @Query("SELECT * FROM items ORDER BY id")
    fun getAllItems(): MutableList<Item?>?

//    @Query("SELECT * FROM items INNER JOIN categories ON category_id = :categoryId")
//    fun getItemsByCategoryId(categoryId: Int): MutableList<Item?>?

    @Query("SELECT * FROM items WHERE id = :id")
    fun getItemById(id: Int): Item?

    @Insert
    fun insertItem(item: Item?) : Long

    @Update
    fun updateItem(item: Item?) : Int

    @Delete
    fun deleteItem(item: Item?) : Int
}