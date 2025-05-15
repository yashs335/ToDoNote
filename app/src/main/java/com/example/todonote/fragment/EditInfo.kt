package com.example.todonote.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.todonote.R
import com.example.todonote.model.UserModel
import com.example.todonote.view.CommunicatorEditProfile

class EditInfo : Fragment(R.layout.edit_info) {
    private  lateinit var communicatorEditProfile : CommunicatorEditProfile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        communicatorEditProfile = activity as CommunicatorEditProfile
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val user_name : EditText = view.findViewById(R.id.edit_text_full_name_edit)
        val user_created_at : EditText = view.findViewById(R.id.edit_text_full_name_created_at)
        val user_email : EditText = view.findViewById(R.id.edit_text_email_edit)
        val user_pass : EditText = view.findViewById(R.id.edit_text__edit_old_pass)
        val user_new_pass : EditText = view.findViewById(R.id.edit_text__edit_new_pass)
        val user_new_conf_pass : EditText = view.findViewById(R.id.edit_text__edit_new_confirm_pass)

        val userModel : UserModel? = communicatorEditProfile.getUser()

        if(userModel!=null){
            user_name.setText(userModel.userName)
            user_email.setText(userModel.userEmail)
            user_created_at.setText(userModel.cretedAt.toString())
        }

        val changeUserName : Button = view.findViewById(R.id.change_user_name)
        val changePass : Button = view.findViewById(R.id.change_password)
        val backButton : ImageButton = view.findViewById(R.id.back_profile_btn_nav)

        backButton.setOnClickListener {
            communicatorEditProfile.setFragment(UserProfile())
        }

        changeUserName.setOnClickListener{
            if(user_name.text.trim().isEmpty()){
                user_name.error = "Please enter new username"
            }else if (user_name.text.trim().toString() == userModel?.userName) {
                user_name.error = "Please enter a new username different from the current one"
            }else{
                communicatorEditProfile.changeUserName(user_name.text.trim().toString())
                communicatorEditProfile.setFragment(EditInfo())
            }
        }

        changePass.setOnClickListener{
            val oldPass = user_pass.text.toString()
            val newPass = user_new_pass.text.toString()
            val newConfPass = user_new_conf_pass.text.toString()
            if (oldPass.isEmpty()) {
                user_pass.error = "Please enter your old password"
            } else if (newPass.isEmpty()) {
                user_new_pass.error = "Please enter your new password"
            } else if (newConfPass.isEmpty()) {
                user_new_conf_pass.error = "Please re-enter your new password"
            } else if (oldPass.length < 8) {
                user_pass.error = "Minimum 8 characters"
            } else if (newPass.length < 8) {
                user_new_pass.error = "Minimum 8 characters"
            } else if (newConfPass.length < 8) {
                user_new_conf_pass.error = "Minimum 8 characters"
            } else if(newPass != newConfPass){
                user_new_pass.error = "Password & Confirm Password do not match"
                user_new_conf_pass.error = "Password & Confirm Password do not match"
            }else if(newPass == oldPass){
                communicatorEditProfile.makeToast("Please enter different password")
            }else if(oldPass == userModel?.userPass){
                val changePass = communicatorEditProfile.changePass(newPass)
                if(changePass){
                    communicatorEditProfile.makeToast("PASSWORD CHANGED")
                    communicatorEditProfile.setFragment(EditInfo())
                }
            }
        }
    }

}