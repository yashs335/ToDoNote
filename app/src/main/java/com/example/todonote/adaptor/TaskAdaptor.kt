package com.example.todonote.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todonote.R
import com.example.todonote.databasehandler.DatabaseHandler
import com.example.todonote.model.TaskModel
import com.example.todonote.view_holder.TaskViewHolder


class TaskAdaptor(
    private val onTaskChanged:(position : Int)->Unit,
    private val onDeleteChanged:(position : Int)->Unit,
    private val context: Context,
    private val taskList: ArrayList<TaskModel>
)
    :
    RecyclerView.Adapter<TaskViewHolder>() {


    private var databaseHandler : DatabaseHandler = DatabaseHandler(
        context = context
    )


    override fun onBindViewHolder(
        holder: TaskViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)

        holder.itemView.setOnClickListener{
            onTaskChanged(position)
        }


        holder.itemView.setOnLongClickListener { v ->
            onDeleteChanged(position)
            true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {

        val view: View = LayoutInflater.from(context).inflate(
            R.layout.raw_task_card,
            parent,
            false
        )

        val viewHolder : TaskViewHolder = TaskViewHolder(view)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
//        holder.itemView.performLongClick()
        holder.title.text = taskList[position].title
        holder.subtitle.text = taskList[position].task
        holder.date.text = taskList[position].updatedAt

    }
}