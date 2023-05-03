package com.fcascan.parcial1.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.fcascan.parcial1.enums.Permissions

@Entity(tableName = "users", indices = [Index(value = ["id", "email"], unique = true)])
class User(
    id: Int,
    permissions: Permissions,
    avatarUrl: String,
    name: String,
    lastName: String,
    email: String,
    password: String,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = id

    @ColumnInfo(name = "permissions")
    var permissions: Permissions = permissions

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String = avatarUrl

    @ColumnInfo(name = "name")
    var name: String = name

    @ColumnInfo(name = "lastName")
    var lastName: String = lastName

    @ColumnInfo(name = "email")
    var email: String = email

    @ColumnInfo(name = "password")
    var password: String = password


    init {
        this.id = id
        this.permissions = permissions
        this.avatarUrl = avatarUrl
        this.name = name
        this.lastName = lastName
        this.email = email
        this.password = password
    }
}