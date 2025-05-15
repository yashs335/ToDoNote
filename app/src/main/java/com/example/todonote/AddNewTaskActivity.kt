package com.example.todonote

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.todonote.databasehandler.DatabaseHandler
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
        val dateFormat = SimpleDateFormat("EEEE, dd-MM-yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(Date())


        taskId = intent.getIntExtra("taskId",-1)
        taskTitle = intent.getStringExtra("taskTitle")
        taskBody = intent.getStringExtra("taskBody")
        taskDate = intent.getStringExtra("taskDate")


        intent.removeExtra("taskId")
        intent.removeExtra("taskTitle")
        intent.removeExtra("taskBody")
        intent.removeExtra("taskDate")

        textViewDate.text = currentDate

        if(taskTitle != null || taskBody != null || taskDate != null){
            textView.text = "Edit note"
            editText_body.setText(taskBody)
            editText_title.setText(taskTitle)
            textViewDate.text = taskDate
            update = true
        }



        button.setOnClickListener{
            if(editText_body.text.trim().isEmpty() || editText_title.text.trim().isEmpty()){
                Toast.makeText(this,"Please full fill the task ",Toast.LENGTH_LONG).show()
            }else{
                if(update){
                    databaseHandler.editTask(editText_title.text.toString(),editText_body.text.toString(),
                        taskId!!.toInt())
                }else{
                    databaseHandler.createNewTask(editText_title.text.toString(),editText_body.text.toString())
                }
                val intent : Intent = Intent(this,MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
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