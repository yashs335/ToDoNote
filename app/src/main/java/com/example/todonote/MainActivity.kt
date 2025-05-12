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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

//        val intent : Intent = Intent()
//
//        startActivity(intent.apply {  })

//        val button : Button = findViewById(R.id.navigate_add_task)
//        button.setOnClickListener{
//            navigateToAddNewTask()
//        }

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