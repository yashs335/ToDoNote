package com.example.todonote.view

interface Communicator {
    fun setFragment(fragment: androidx.fragment.app.Fragment)
    fun getLogin(email : String,pass : String)
    fun getSignUp(email : String,pass : String,userName: String)
}