package com.example.todonote

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.todonote.communicator.Communicator
import com.example.todonote.fragment.LoginFragment

class LoginActivity : AppCompatActivity() , Communicator{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        setFragment(LoginFragment())

    }

    override fun setFragment(fragment: Fragment) {
        Log.i("nav","navigation run in ${fragment.javaClass}")
        val fragmentManager = supportFragmentManager
        val fragmentTransient = fragmentManager.beginTransaction()
        fragmentTransient.replace(R.id.fragment_container,fragment).commit()
    }

}