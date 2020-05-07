package com.example.todoapplication

import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todoapplication.database.AppDatabase
import com.example.todoapplication.database.entity.ToDoItem
import kotlinx.android.synthetic.main.activity_to_do_add.*

class ToDoAddActivity : AppCompatActivity() {
    companion object{
        var priority: String = ""
        const val EXTRA_EDIT = "edit"
        const val EXTRA_ADD = "add"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_add)

        val userId = intent.getIntExtra(ToDoListActivity.EXTRA_USERID, 0)
        val itemId = intent.getIntExtra(ToDoDetailsActivity.EXTRA_ITEM_ID, 0)

        addNewItem(userId)
        updateItem(itemId)
        priorityType()
    }
    private fun priorityType(){
        low_btn.setOnClickListener{
            low_btn.setBackgroundColor(Color.BLUE)
            priority = "low"
        }
        high_btn.setOnClickListener{
            high_btn.setBackgroundColor(Color.BLUE)
            priority = "high"
        }
        medium_btn.setOnClickListener{
            medium_btn.setBackgroundColor(Color.BLUE)
            priority = "medium"
        }
    }
    private fun updateItem(itemId: Int){
        AsyncTask.execute{
            val item = AppDatabase.getInstance(applicationContext)?.getItemDao()?.getItemById(itemId)
            runOnUiThread {
                if (item != null) {
                    get_item_title.setText(item.title)
                    get_item_text.setText(item.description)
                    get_item_date.setText(item.dueDate)
                }
            }
        }
        save_btn.setOnClickListener{
            AsyncTask.execute{
                AppDatabase.getInstance(applicationContext)?.getItemDao()?.updateItem(
                    title = get_item_title.text.toString(), desc = get_item_text.text.toString(), priority = priority,
                    dueDate = get_item_date.text.toString(), itemId = itemId
                )

                runOnUiThread{
                    finish()
                }
            }
        }
    }
    private fun addNewItem(userId: Int){
        save_btn.setOnClickListener{
            AsyncTask.execute{
                AppDatabase.getInstance(applicationContext)?.getItemDao()?.insertItem(ToDoItem(
                    title = get_item_title.text.toString(), description = get_item_text.text.toString(), priority = priority,
                    dueDate = get_item_date.text.toString(), userItemId = userId)
                )
                runOnUiThread{
                    finish()
                }
            }
        }
    }


}
