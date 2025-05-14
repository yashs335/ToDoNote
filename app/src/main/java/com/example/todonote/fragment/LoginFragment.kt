package com.example.todonote.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.todonote.R
import com.example.todonote.communicator.Communicator

class LoginFragment : Fragment(R.layout.sign_in_screen) {

    private lateinit var communicator: Communicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        communicator = activity as Communicator

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView?>(R.id.sign_up_nav_button)?.setOnClickListener {
            Log.i("nav","navigation run in sign up")
            communicator.setFragment(SignUpScreen())
        }
    }

}