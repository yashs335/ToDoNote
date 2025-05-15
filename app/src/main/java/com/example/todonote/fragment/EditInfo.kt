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
            communicatorEditProfile.changeUserName(user_name.text.toString())
        }

        changePass.setOnClickListener{
            communicatorEditProfile.changePass(user_pass.text.toString(),user_new_pass.text.toString())
        }
    }

}