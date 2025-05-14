package com.example.todonote.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.todonote.R
import com.example.todonote.presenter.Presenter
import com.example.todonote.view.Communicator

class SignUpScreen() : Fragment(R.layout.sign_up_screen) {

    private lateinit var communicator: Communicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        communicator = activity as Communicator

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = view.findViewById<EditText>(R.id.edit_text_email_signup)
        val pass = view.findViewById<EditText>(R.id.edit_text_password_signup)
        val userName = view.findViewById<EditText>(R.id.edit_text_full_name_signup)

        view.findViewById<TextView?>(R.id.sign_in_nav_button)?.setOnClickListener {
            communicator.setFragment(LoginFragment())
        }

        view.findViewById<Button>(R.id.sign_up_button)?.setOnClickListener{
            communicator.getSignUp(email.text.toString(),pass.text.toString(),userName.text.toString())
        }


    }
}