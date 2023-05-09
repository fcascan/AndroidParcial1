package com.fcascan.parcial1.dao

import androidx.room.*
import com.fcascan.parcial1.entities.Item
import java.sql.RowId

@Dao
interface ItemDao {
    @Query("SELECT * FROM items ORDER BY id")
    fun getAllItems(): MutableList<Item?>?

//    @Query("SELECT * FROM items INNER JOIN categories ON category_id = :categoryId")
//    fun getItemsByCategoryId(categoryId: Int): MutableList<Item?>?

    @Query("SELECT * FROM items WHERE id = :id")
    fun getItemById(id: Int): Item?

//    @Query("SELECT * FROM items WHERE id = :id")
//    fun getItemIdJustAdded(id: Int): Int?

    @Insert
    fun insertItem(item: Item?) : Long

    fun insertItemReturnId(item: Item?) : Int {
        return getItemById(insertItem(item).toInt())!!.id
    }

//    fun insertItemReturnId(item: Item?) : Int {
//        //Since insert returns the rowId, we can use it to get the id of the item just added
//        val result1 = insertItem(item)
//        if (result1 == -1) {
//            return -1
//        }
//        val result2 = getItemIdJustAdded(result1)
//        if (result2 == null) {
//            return -1
//        } else
//            return result2
//    }

    @Update
    fun updateItem(item: Item?) : Int

    @Delete
    fun deleteItem(item: Item?) : Int
}