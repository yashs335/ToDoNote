package com.example.todonote

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todonote.presenter.Presenter

class EditProfile : AppCompatActivity() {

    private var presenter : Presenter = Presenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_profile)

        val editInfo : Boolean = false

        val user_name : EditText = findViewById(R.id.edit_text_full_name_signup)
        val user_email : EditText = findViewById(R.id.edit_text_email_signup)
        val user_pass : EditText = findViewById(R.id.edit_text__edit_old_pass)
        val user_new : EditText = findViewById(R.id.edit_text__edit_new_pass)

        val logOutbutton : Button = findViewById(R.id.log_out_button)
        val backButton : ImageButton = findViewById(R.id.back_profile_btn)



        backButton.setOnClickListener {
            finish()
        }

        logOutbutton.setOnClickListener{
            presenter.logOut()
            val intent: Intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            this.startActivity(intent)
        }

    }
}