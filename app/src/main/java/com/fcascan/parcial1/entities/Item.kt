package com.fcascan.parcial1.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "items", indices = [Index(value = ["id", "name"], unique = true)])
class Item(
    id: Int,
    name: String,
    photoUrl: String,
//    categories: MutableList<Category>,
    description: String,
    field1: String,
    field2: String,
    field3: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = id

    //TODO: asociar varias categorias a un item??
    //TODO: asociar un usuario a varios item??

    @ColumnInfo(name = "name")
    var name: String = name

    @ColumnInfo(name = "photoUrl")
    var photoUrl: String = photoUrl

    @ColumnInfo(name = "description")
    var description: String = description

//    @ColumnInfo(name = "Categories")
//    var categories: MutableList<Category> = categories

    @ColumnInfo(name = "field1")
    var field1: String = field1

    @ColumnInfo(name = "field2")
    var field2: String = field2

    @ColumnInfo(name = "field3")
    var field3: String = field3

    init {
        this.id = id
        this.name = name
        this.photoUrl = photoUrl
        this.description = description
//        this.categories = categories
        this.field1 = field1
        this.field2 = field2
        this.field3 = field3
    }
}