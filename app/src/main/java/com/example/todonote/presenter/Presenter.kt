package com.example.todonote.presenter

import android.content.Context
import com.example.todonote.databasehandler.DatabaseHandler
import com.example.todonote.model.UserModel
import com.example.todonote.view.ViewAuth

class Presenter(val context: Context) : ViewAuth{
    private lateinit var databaseHandler: DatabaseHandler

    override fun loginUser(email: String, pass: String): Boolean {
        databaseHandler = DatabaseHandler(context)
        return databaseHandler.loginUser(email, pass)
    }

    override fun signUp(email: String, pass: String, userName: String): Boolean {
        databaseHandler = DatabaseHandler(context)
        return databaseHandler.signUp(email, pass, userName)
    }

    override fun logOut() {
        databaseHandler = DatabaseHandler(context)
        return databaseHandler.logOut()
    }

    override fun getUser(): UserModel? {
        databaseHandler = DatabaseHandler(context)
        return databaseHandler.getUser()
    }

    override fun changePass(oldPass: String, newPass: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun changeUserName(userName: String): Boolean {
        TODO("Not yet implemented")
    }

}