package com.example.todonote.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.todonote.LoginActivity
import com.example.todonote.R
import com.example.todonote.model.UserModel
import com.example.todonote.view.CommunicatorEditProfile

class UserProfile : Fragment(R.layout.profile_screen_layout) {

    private lateinit var communicatorEditProfile: CommunicatorEditProfile

    override fun onCreate(savedInstanceState: Bundle?) {
        communicatorEditProfile = activity as CommunicatorEditProfile
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val logOutbutton : Button = view.findViewById(R.id.log_out_button)
        val backButton : ImageButton = view.findViewById(R.id.back_profile_btn)
        val editButton : Button = view.findViewById(R.id.edit_button)

        val userModel : UserModel? = communicatorEditProfile.getUser()

        if(userModel!=null){
            view.findViewById<TextView>(R.id.user_name).setText(userModel.userName)
            view.findViewById<TextView>(R.id.user_email).setText(userModel.userEmail)
        }

        editButton.setOnClickListener{
            communicatorEditProfile.setFragment(EditInfo())
        }

        backButton.setOnClickListener {
            communicatorEditProfile.returnHomeScreen()
        }

        logOutbutton.setOnClickListener{
            communicatorEditProfile.logout()
        }
    }
}