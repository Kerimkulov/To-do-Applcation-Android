package com.example.notetakeapproom

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapplication.R
import com.example.todoapplication.database.entity.ToDoItem
import kotlinx.android.synthetic.main.layout_item.view.*

class MyAdapter(
    private val items: List<ToDoItem> = listOf(),
    private val onItemClick:(ToDoItem) -> Unit
): RecyclerView.Adapter<MyAdapter.NoteViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bindNote(items[position])
    }
    inner class NoteViewHolder(
        private  val view: View
    ): RecyclerView.ViewHolder(view) {
        @SuppressLint("ResourceAsColor")
        fun bindNote(item: ToDoItem){
            view.note_title_view.text = item.title
            view.note_text_view.text = item.description
            view.note_date_view.text = item.dueDate
            when (item.priority) {
                "low" -> {
                    view.priority_color.setBackgroundColor(Color.GREEN)
                }
                "medium" -> {
                    view.priority_color.setBackgroundColor(Color.YELLOW)
                }
                "high" -> {
                    view.priority_color.setBackgroundColor(Color.RED)
                }
            }
            view.setOnClickListener{
                onItemClick(item)
            }
        }
    }

}

