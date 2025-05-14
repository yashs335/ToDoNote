package com.example.todonote.view_holder

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.todonote.R
import org.w3c.dom.Text

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val title : TextView = itemView.findViewById(R.id.title)
    val subtitle : TextView = itemView.findViewById(R.id.sub_title)
    val date: TextView = itemView.findViewById(R.id.task_date)



    init {
        super.itemView
        title
        subtitle
        date
    }

}
