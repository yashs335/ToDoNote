package com.example.todonote

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.todonote.fragment.EditInfo
import com.example.todonote.fragment.UserProfile
import com.example.todonote.model.UserModel
import com.example.todonote.presenter.Presenter
import com.example.todonote.view.Communicator
import com.example.todonote.view.CommunicatorEditProfile

class EditProfile : AppCompatActivity() , CommunicatorEditProfile{

    private var presenter : Presenter = Presenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_profile)

        val editInfo : Boolean = false

        setFragment(UserProfile())



    }

    override fun setFragment(fragment: Fragment) {
        Log.i("nav","navigation run in ${fragment.javaClass}")
        val fragmentManager = supportFragmentManager
        val fragmentTransient = fragmentManager.beginTransaction()
        fragmentTransient.replace(R.id.edit_profile_fragment_container,fragment).commit()
    }

    override fun getUser(): UserModel? {
        return presenter.getUser()
    }

    override fun changePass(newPass: String): Boolean {
//        Toast.makeText(this,"Change password call ",Toast.LENGTH_LONG).show()
        return presenter.changePass(newPass)
    }



    override fun changeUserName(userName: String): Boolean {
//        Toast.makeText(this,"Change username call ",Toast.LENGTH_LONG).show()
            return presenter.changeUserName(userName)
    }

    override fun logout() {
        presenter.logOut()
        val intent: Intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        this.startActivity(intent)
    }

    override fun returnHomeScreen() {
        finish()
    }
}