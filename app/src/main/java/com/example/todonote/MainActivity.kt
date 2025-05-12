package com.example.todonote

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.todonote.handler.DatabaseHandler
import com.example.todonote.model.TaskModel

class MainActivity : AppCompatActivity() {

    private val databaseHandler : DatabaseHandler = DatabaseHandler(this)

    private var taskList : ArrayList<TaskModel> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        taskList = databaseHandler.readAllTask()
//
//        startActivity(intent.apply {  })

        val button : Button = findViewById(R.id.navigate_add_task)
        button.setOnClickListener{
            val intent : Intent = Intent(this,AddNewTaskActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

//    private fun navigateToAddNewTask(){
//        val fragmentManager : FragmentManager = supportFragmentManager
//        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
//
//        val addNewTask : AddNewTask = AddNewTask()
//        fragmentTransaction.replace(R.id.add_task_fragment,addNewTask)
//        fragmentTransaction.addToBackStack(null)
//        fragmentTransaction.commit()
//
//    }
}