package com.example.todonote.fragment

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.todonote.R
import com.example.todonote.validation.validation
import com.example.todonote.view.Communicator
import org.intellij.lang.annotations.Pattern

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
            if(email.text.trim().isEmpty() || pass.text.trim().isEmpty()){
                email.error = "Please enter the email"
                pass.error = "please enter the pass"
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher( email.text.toString()).matches()) {
                email.error = "Please Enter Valid Address"
            }else if(pass.text.length < 8){
                pass.error = "Please Enter minimum 8 character"
            } else{
//                Toast.makeText(context,"login checked",Toast.LENGTH_LONG).show()
                communicator.getLogin(email.text.toString(),pass.text.toString())
            }
        }


    }

}