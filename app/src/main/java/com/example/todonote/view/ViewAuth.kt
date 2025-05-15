package com.example.todonote.view

import com.example.todonote.model.TaskModel
import com.example.todonote.model.UserModel
import java.util.ArrayList

interface ViewAuth{
    fun loginUser(email : String,pass : String):Boolean
    fun signUp(email : String,pass: String,userName : String):Boolean
    fun logOut()
    fun getUser() : UserModel?
    fun changePass(oldPass : String,newPass : String) : Boolean
    fun changeUserName(userName: String) : Boolean
}