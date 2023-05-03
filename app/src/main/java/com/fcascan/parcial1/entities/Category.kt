package com.fcascan.parcial1.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "categories", indices = [Index(value = ["id", "name"], unique = true)])
class Category(
    id: Int,
    name: String,
    description: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = id

    @ColumnInfo
    var name: String = name

    @ColumnInfo
    var description: String = description

    init {
        this.id = id
        this.name = name
        this.description = description
    }
}