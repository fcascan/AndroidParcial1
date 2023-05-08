package com.fcascan.parcial1.dao

import androidx.room.*
import com.fcascan.parcial1.entities.Item
import com.fcascan.parcial1.entities.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY id")
    fun getAllUsers(): MutableList<User?>?

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUserById(id: Int): User?

    @Query("SELECT * FROM users WHERE email = :email")
    fun getUserByEmail(email: String): User

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    fun getUserByEmailAndPassword(email: String, password: String): User

    @Query("SELECT * FROM users WHERE email = :email")
    fun getUserIdByEmail(email: String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User?) : Long

    @Update
    fun updateUser(user: User?) : Int

    @Delete
    fun deleteUser(user: User?) : Int
}