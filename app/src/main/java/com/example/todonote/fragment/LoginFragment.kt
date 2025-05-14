package com.example.todonote.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.todonote.R
import com.example.todonote.view.Communicator

class LoginFragment : Fragment(R.layout.sign_in_screen) {

    private lateinit var communicator: Communicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        communicator = activity as Communicator

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = view.findViewById<EditText>(R.id.edit_text_email_login)
        val pass = view.findViewById<EditText>(R.id.edit_text_password_login)

        view.findViewById<TextView?>(R.id.sign_up_nav_button)?.setOnClickListener {
            communicator.setFragment(SignUpScreen())
        }
        view.findViewById<Button>(R.id.login_button)?.setOnClickListener{
            communicator.getLogin(email.text.toString(),pass.text.toString())
        }
    }

}