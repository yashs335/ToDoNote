package com.example.todonote

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todonote.view.Communicator
import com.example.todonote.fragment.LoginFragment
import com.example.todonote.handler.DatabaseHandler
import com.example.todonote.presenter.Presenter
import com.example.todonote.view.ViewAuth

class LoginActivity : AppCompatActivity() , Communicator{

    private lateinit var presenter: Presenter
    private lateinit var email : String
    private lateinit var pass : String
    private lateinit var userName : String

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

    override fun getLogin(email: String, pass: String) {
        presenter = Presenter(this)
        presenter.loginUser(email,pass)
        val intent : Intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun getSignUp(email: String, pass: String, userName: String) {
        presenter =Presenter(this)
        presenter.signUp(email,pass,userName)
       val intent : Intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }


}