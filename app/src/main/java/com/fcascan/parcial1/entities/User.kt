package com.fcascan.parcial1.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.fcascan.parcial1.enums.Permissions

@Entity(tableName = "users", indices = [Index(value = ["id", "email"], unique = true)])
class User(
    id: Int,
    name: String,
    lastName: String,
    email: String,
    password: String,
    permissions: Permissions,
    avatarUrl: String,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = id

    @ColumnInfo(name = "name")
    var name: String = name

    @ColumnInfo(name = "lastName")
    var lastName: String = lastName

    @ColumnInfo(name = "email")
    var email: String = email

    @ColumnInfo(name = "password")
    var password: String = password

    @ColumnInfo(name = "permissions")
    var permissions: Permissions = permissions

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String = avatarUrl

    init {
        this.id = id
        this.name = name
        this.lastName = lastName
        this.email = email
        this.password = password
        this.permissions = permissions
        this.avatarUrl = avatarUrl
    }
}