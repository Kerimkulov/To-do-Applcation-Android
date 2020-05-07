package com.example.todoapplication.database.dao

import androidx.room.*
import com.example.todoapplication.database.entity.ToDoItem
import com.example.todoapplication.database.entity.User

@Dao
interface UserDAO {
    @Insert
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query(value = "Select * FROM users")
    fun getNotes():List<User>

    @Query(value = "SELECT * FROM users WHERE userId= :id")
    fun getUserById(id: Int): User

    @Query(value = "SELECT * FROM users WHERE username= :username")
    fun getUserByUsername(username: String): User


}