package com.example.todoapplication

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notetakeapproom.MyAdapter
import com.example.todoapplication.database.AppDatabase
import com.example.todoapplication.database.entity.ToDoItem
import kotlinx.android.synthetic.main.activity_to_do_list.*

class ToDoListActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_USERID = "userid"
        const val EXTRA_TITLE = "title"
        const val EXTRA_TEXT = "text"
        const val EXTRA_DATE = "date"
        const val EXTRA_PRIORITY = "priority"
        const val ADD_TASK_REQUEST = 1
        const val DELETE_TASK_REQUEST = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)
        val userid = intent.getIntExtra(EXTRA_USERID, 0)


        addItem(userid)
        initListItem(userid)


    }

    override fun onResume() {
        val userid = intent.getIntExtra(EXTRA_USERID, 0)
        super.onResume()
        initListItem(userid)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ( requestCode == DELETE_TASK_REQUEST){
            if (resultCode == Activity.RESULT_OK){
                Log.d("del", "DELETED")
                val position = data?.getIntExtra(ToDoDetailsActivity.EXTRA_ITEM_ID, 0)

                position?.let {
                    Log.d("taag", "YESSSSSSSSSSSSS")
                    AsyncTask.execute {
                        val item = AppDatabase.getInstance(applicationContext)?.getItemDao()?.getItemById(position)
                        if (item != null) {
                            AppDatabase.getInstance(applicationContext)?.getItemDao()?.deleteItem(item)
                        }
                    }
                }
            }
        }
    }

    private fun initListItem(userid: Int){
        init_list_item.layoutManager = LinearLayoutManager(this)

        AsyncTask.execute {
            val items = AppDatabase.getInstance(applicationContext)
                ?.getItemDao()?.getUsersItems(userid)

            runOnUiThread{
                init_list_item.adapter = items?.let {
                    MyAdapter(items = it, onItemClick = {
                        val intent = Intent(this, ToDoDetailsActivity::class.java)
                        intent.putExtra(ToDoDetailsActivity.EXTRA_ITEM_ID, it.itemId)
                        startActivity(intent)
                    })
                }
            }
        }
        designRecyclerView()
    }
    private fun designRecyclerView(){
        (init_list_item.layoutManager as LinearLayoutManager).reverseLayout = true
        (init_list_item.layoutManager as LinearLayoutManager).stackFromEnd = true
        val mDividerItemDecoration = DividerItemDecoration(
            init_list_item.context,
            (init_list_item.layoutManager as LinearLayoutManager).orientation
        )
        init_list_item.addItemDecoration(mDividerItemDecoration)
    }
    private fun addItem(id: Int){
        add_item_btn.setOnClickListener{
            val intent = Intent(this, ToDoAddActivity::class.java)
            intent.putExtra(EXTRA_USERID, id)
            startActivity(intent)
        }
    }
}
