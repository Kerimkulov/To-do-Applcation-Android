package com.example.todoapplication.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.todoapplication.database.entity.ToDoItem

@Dao
interface ToDoItemDAO {
    @Query("Select * from items Where userItemId= :id AND done = 0")
    fun getUsersItems(id: Int): List<ToDoItem>

    @Insert
    fun insertItem(item: ToDoItem)

    @Delete
    fun deleteItem(item: ToDoItem)

    @Query("Select * from items Where itemId= :id")
    fun getItemById(id: Int): ToDoItem

    @Query(value = "UPDATE items SET done= :done WHERE itemId= :id")
    fun updateItemDone(done: Boolean, id: Int)

    @Query(value = "UPDATE items SET title= :title, description= :desc, dueDate= :dueDate, priority= :priority WHERE itemId= :itemId")
    fun updateItem(title: String, desc: String, dueDate: String, priority: String, itemId: Int)

}