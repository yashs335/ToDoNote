package com.example.todonote

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todonote.handler.DatabaseHandler
import com.example.todonote.model.TaskModel

class AddNewTaskActivity : AppCompatActivity() {

    private val databaseHandler : DatabaseHandler = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_new_task)

        val button : Button = findViewById(R.id.add_task_button)
        val editText_title : EditText = findViewById(R.id.task_title_edit_text)
        val editText_body : EditText = findViewById(R.id.task_body_edit_text)

        button.setOnClickListener{
            if(editText_body.text.trim().isEmpty() || editText_title.text.trim().isEmpty()){
                Toast.makeText(this,"Full fill the data",Toast.LENGTH_LONG).show()
            }else{
                val taskmodel : TaskModel = TaskModel(1,editText_title.text.toString(),editText_body.text.toString())
                databaseHandler.createNewTask(taskmodel)
                startActivity(Intent(this,MainActivity::class.java))
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}