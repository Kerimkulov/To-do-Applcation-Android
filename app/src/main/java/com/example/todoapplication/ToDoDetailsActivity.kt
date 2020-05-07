package com.example.todoapplication

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todoapplication.database.AppDatabase
import kotlinx.android.synthetic.main.activity_to_do_details.*

class ToDoDetailsActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_ITEM_ID = "itemID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_details)

        val itemId = intent.getIntExtra(EXTRA_ITEM_ID, 0)
        itemDetial(itemId)
        markDoneItem(itemId)
        editItem(itemId)
    }

    private fun itemDetial(itemId: Int){
        AsyncTask.execute {
            val item = AppDatabase.getInstance(applicationContext)?.getItemDao()?.getItemById(itemId)
            if(item!=null) {
                runOnUiThread {
                    detail_text.text = item.description
                    detail_title.text = item.title
                    detail_date.text = item.dueDate
                    detail_priority.text = item.priority
                }
            }
        }
    }
    private fun editItem(itemId: Int){
        edit_btn.setOnClickListener{
            val intent = Intent(this, ToDoAddActivity::class.java)
            intent.putExtra(EXTRA_ITEM_ID, itemId)
            startActivity(intent)
            finish()
        }
    }
    private fun markDoneItem(itemId: Int){
        mark_done_btn.setOnClickListener{
            AsyncTask.execute{
                AppDatabase.getInstance(applicationContext)?.getItemDao()?.updateItemDone(true, itemId)
                runOnUiThread{
                    finish()
                }
            }
        }
    }
}
