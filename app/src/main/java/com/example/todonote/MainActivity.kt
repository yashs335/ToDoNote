package com.example.todonote

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todonote.adaptor.TaskAdaptor
import com.example.todonote.handler.DatabaseHandler
import com.example.todonote.model.TaskModel

class MainActivity : AppCompatActivity() {

    private val databaseHandler : DatabaseHandler = DatabaseHandler(this)

    private var taskList : ArrayList<TaskModel> = ArrayList()
    lateinit var recyclerAdaptor : TaskAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

//
//        startActivity(intent.apply {  })

//        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager =  LinearLayoutManager(this)


        taskList = databaseHandler.readAllTask()


        recyclerAdaptor = TaskAdaptor(
            { position: Int -> delVal(position)
            },
            this,
            taskList
        )


        recyclerView.adapter = recyclerAdaptor


        val button : Button = findViewById(R.id.navigate_add_task)
        button.setOnClickListener{
            val intent : Intent = Intent(this,AddNewTaskActivity::class.java)
            startActivity(intent)
        }

    }

    fun delVal(position : Int)  {
        val taskId = taskList[position].id
        taskList.removeAt(position)
        databaseHandler.deleteTheTask(taskId)
        recyclerAdaptor.notifyItemRemoved(position)
    }
}