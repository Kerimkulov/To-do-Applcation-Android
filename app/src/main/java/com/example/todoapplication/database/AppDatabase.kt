package com.example.todoapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapplication.database.dao.ToDoItemDAO
import com.example.todoapplication.database.dao.UserDAO
import com.example.todoapplication.database.entity.ToDoItem
import com.example.todoapplication.database.entity.User

@Database(entities = [ToDoItem::class, User::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getUserDao(): UserDAO
    abstract fun getItemDao(): ToDoItemDAO
    companion object{
        private const val DB_NAME = "todo.db"
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase?{
            if(instance == null){
                instance = Room.databaseBuilder(context,
                    AppDatabase::class.java, DB_NAME).build()
            }
            return instance
        }
    }
}