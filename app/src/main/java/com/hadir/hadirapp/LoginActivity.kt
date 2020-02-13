package com.hadir.hadirapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.hadir.hadirapp.ui.base.BaseActivity
import com.hadir.hadirapp.ui.login.LoginFragment

class LoginActivity : BaseActivity() {

    companion object {
        val RC_SIGN_IN: Int = 1
        val user = FirebaseAuth.getInstance().currentUser

    }

    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
       addFragment(LoginFragment())

    }

    //get firebase instance to send it to db
    override fun onStart() {
        super.onStart()
        val  user = FirebaseAuth.getInstance().currentUser
        if(user != null){
            startActivity(MainActivity.getLaunchIntent(this))
            finish()
        }

    }



}
