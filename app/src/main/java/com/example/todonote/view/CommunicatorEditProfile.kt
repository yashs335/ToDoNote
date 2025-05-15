package com.example.todonote.view

import com.example.todonote.model.UserModel

interface CommunicatorEditProfile {
    fun setFragment(fragment: androidx.fragment.app.Fragment)
    fun getUser() : UserModel?
    fun changePass(newPass : String) : Boolean
    fun changeUserName(userName: String) : Boolean
    fun logout()
    fun returnHomeScreen()
    fun makeToast(mes : String)
}