package com.example.todoapplication.database.entity

import android.content.ClipDescription
import android.icu.text.CaseMap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ToDoItem(
    @PrimaryKey(autoGenerate = true) val itemId: Int? = null,
    @ColumnInfo(name = "userItemId") val userItemId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "dueDate") val dueDate: String,
    @ColumnInfo(name = "priority") val priority: String,
    @ColumnInfo(name = "done") val done: Boolean = false
)