package com.example.todonote

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todonote.adaptor.TaskAdaptor
import com.example.todonote.databasehandler.DatabaseHandler
import com.example.todonote.model.TaskModel

class MainActivity : AppCompatActivity() {

    private val databaseHandler : DatabaseHandler = DatabaseHandler(this)

    private var taskList : ArrayList<TaskModel> = ArrayList()
    private lateinit var recyclerAdaptor : TaskAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val sharedPreferences = this.getSharedPreferences("user", MODE_PRIVATE)
        val email : String = sharedPreferences.getString("user_email","").toString()

        if(email.isEmpty()){
            val intent : Intent = Intent(this,LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }else{
            taskList = databaseHandler.readAllTask(email)
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this)

            recyclerAdaptor = TaskAdaptor(
                { position: Int ->
                    updateValue(position)
                },
                { position: Int ->
                    delVal(position)
                },
                this,
                taskList
            )
            recyclerView.adapter = recyclerAdaptor

        }




        val button : Button = findViewById(R.id.navigate_add_task)
        button.setOnClickListener{
            val intent : Intent = Intent(this,AddNewTaskActivity::class.java)
            startActivity(intent)
        }

        val editProfileButton : ImageButton = findViewById(R.id.profile_edit_nav)
        editProfileButton.setOnClickListener{
            val intent: Intent = Intent(this, EditProfile::class.java)
            this.startActivity(intent)

//            databaseHandler.logOut()
//            val intent: Intent = Intent(this, LoginActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            this.startActivity(intent)
        }

    }

    private fun updateValue(position: Int){
        val taskModel = taskList[position]
        val intent : Intent = Intent(this,AddNewTaskActivity()::class.java)

        intent.putExtra("taskId",taskModel.id)
        intent.putExtra("taskTitle",taskModel.title)
        intent.putExtra("taskBody",taskModel.task)
        intent.putExtra("taskDate",taskModel.updatedAt)


        startActivity(intent)
    }

    private fun delVal(position : Int)  {
        val taskId = taskList[position].id
        val taskModel = taskList.get(position)

        val builder = AlertDialog.Builder(this)

        builder.setTitle("Delete Note")
            .setMessage("Do you really delete the note ? \n ${taskModel.title} \n ${taskModel.task.to(20)}")
            .setPositiveButton("Yes"){dialog,which ->
                taskList.removeAt(position)
                databaseHandler.deleteTheTask(taskId)
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