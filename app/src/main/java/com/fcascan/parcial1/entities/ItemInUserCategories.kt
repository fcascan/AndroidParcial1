package com.fcascan.parcial1.entities

import androidx.room.*

@Entity(tableName = "items_in_user_categories")
class ItemInUserCategories(
    id: Int,
    item_id: Int,
    user_categories_id: Int
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = id

    @ColumnInfo(name = "item_id")
    var item_id: Int = item_id

    @ColumnInfo(name = "user_with_category_id")
    var user_categories_id: Int = user_categories_id

    init {
        this.id = id
        this.item_id = item_id
        this.user_categories_id = user_categories_id
    }
}