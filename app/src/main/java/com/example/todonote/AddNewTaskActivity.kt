package com.example.todonote

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import com.example.todonote.handler.DatabaseHandler
import com.example.todonote.model.TaskModel

class AddNewTaskActivity() : AppCompatActivity() {

    private val databaseHandler : DatabaseHandler = DatabaseHandler(this)
    private var taskId: Int? = null
    private var taskTitle: String? = null
    private var taskBody: String? = null
    private var taskDate: String? = null
    private var update: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_new_task)

        val button : ImageButton = findViewById(R.id.add_task_button_done)
        val backButton : ImageButton = findViewById(R.id.back_btn)
        val resetButton : ImageButton = findViewById(R.id.reset_button)

        val editText_title : EditText = findViewById(R.id.task_title_edit_text)
        val editText_body : EditText = findViewById(R.id.task_body_edit_text)
        val textView : TextView = findViewById(R.id.app_bar_title)
        val textViewDate : TextView = findViewById(R.id.date)


        taskId = intent.getIntExtra("taskId",-1)
        taskTitle = intent.getStringExtra("taskTitle")
        taskBody = intent.getStringExtra("taskBody")
        taskDate = intent.getStringExtra("taskDate")


        intent.removeExtra("taskId")
        intent.removeExtra("taskTitle")
        intent.removeExtra("taskBody")
        intent.removeExtra("taskDate")

        if(taskTitle != null || taskBody != null || taskDate != null){
            textView.setText("Edit note")
            editText_body.setText(taskBody)
            editText_title.setText(taskTitle)
            textViewDate.setText(taskDate)
            update = true
        }



        button.setOnClickListener{
            if(editText_body.text.trim().isEmpty() || editText_title.text.trim().isEmpty()){
                Toast.makeText(this,"Please full fill the task ",Toast.LENGTH_LONG).show()
            }else{
                databaseHandler.createNewTask(taskId,editText_title.text.toString(),editText_body.text.toString(),update)
                startActivity(Intent(this,MainActivity::class.java))
            }
        }

        backButton.setOnClickListener {
            finish()
        }

        resetButton.setOnClickListener {
            editText_body.setText("")
            editText_title.setText("Untitle")
        }

    }
}