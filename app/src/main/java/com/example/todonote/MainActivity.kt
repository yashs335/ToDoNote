package com.example.todonote

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todonote.adaptor.TaskAdaptor
import com.example.todonote.handler.NoteDatabaseHandler
import com.example.todonote.model.TaskModel

class MainActivity : AppCompatActivity() {

    private val noteDatabaseHandler : NoteDatabaseHandler = NoteDatabaseHandler(this)

    private var taskList : ArrayList<TaskModel> = ArrayList()
    lateinit var recyclerAdaptor : TaskAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        finish()
        setContentView(R.layout.activity_main)

        val intent : Intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)

//
//        startActivity(intent.apply {  })

//        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager =  LinearLayoutManager(this)


        taskList = noteDatabaseHandler.readAllTask()


        recyclerAdaptor = TaskAdaptor(
            {
                position: Int -> updateValue(position)
            },
            {
                position: Int ->  delVal(position)
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

    fun updateValue(position: Int){
        val taskId = taskList[position].id
        val taskModel = taskList[position]
        val intent : Intent = Intent(this,AddNewTaskActivity()::class.java)

        intent.putExtra("taskId",taskModel.id)
        intent.putExtra("taskTitle",taskModel.title)
        intent.putExtra("taskBody",taskModel.task)
        intent.putExtra("taskDate",taskModel.date)


        startActivity(intent)
    }

    fun delVal(position : Int)  {
        val taskId = taskList[position].id
        val taskModel = taskList.get(position)

        val builder = AlertDialog.Builder(this)

        builder.setTitle("Delete Note")
            .setMessage("Do you really delete the note ? \n ${taskModel.title} \n ${taskModel.task.to(20)}")
            .setPositiveButton("Yes"){dialog,which ->
                taskList.removeAt(position)
                noteDatabaseHandler.deleteTheTask(taskId)
                recyclerAdaptor.notifyItemRemoved(position)
                Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton("No"){dialog,which->
                dialog.dismiss()
            }

        val alertDialog : AlertDialog = builder.create()
        alertDialog.show()
    }
}