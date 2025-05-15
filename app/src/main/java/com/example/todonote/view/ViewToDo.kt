package com.example.todonote.view

import com.example.todonote.model.TaskModel
import java.util.ArrayList

interface ViewToDo {
    fun createNewTask(title : String,body : String)
    fun editTask(title : String,body : String,taskId : Int)
    fun readAllTask(email: String) : ArrayList<TaskModel>
    fun deleteTheTask(id : Int)

}