package com.fcascan.parcial1.entities

import androidx.room.*

@Entity(tableName = "user_categories")
class UserCategories(
//    @Embedded val user: User,
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "userId"
//    )
//    val categories: List<Category>
    id: Int,
    user_id: Int,
    category_id: Int
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = id

    @ColumnInfo(name = "user_id")
    var user_id: Int = user_id

    @ColumnInfo(name = "category_id")
    var category_id: Int = category_id

    init {
        this.id = id
        this.user_id = user_id
        this.category_id = category_id
    }
}