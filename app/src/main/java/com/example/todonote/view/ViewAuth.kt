package com.example.todonote.view

import com.example.todonote.model.TaskModel
import java.util.ArrayList

interface ViewAuth{
    fun loginUser(email : String,pass : String):Boolean
    fun signUp(email : String,pass: String,userName : String):Boolean
    fun logOut()
}