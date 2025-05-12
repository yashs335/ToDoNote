package com.example.todonote.view_holder

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todonote.R
import org.w3c.dom.Text

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val title : TextView = itemView.findViewById(R.id.title)
    val subtitle : TextView = itemView.findViewById(R.id.sub_title)
    val button : Button = itemView.findViewById(R.id.complete_button)

    init {
        super.itemView
        title
        subtitle
        button
    }

}
