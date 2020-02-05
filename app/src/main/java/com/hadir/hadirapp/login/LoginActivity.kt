package com.hadir.hadirapp.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.hadir.hadirapp.MainActivity
import com.hadir.hadirapp.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private var clicked = false

    companion object {
        const val RC_SIGN_IN: Int = 1
        fun getLaunchIntent(from: Context) = Intent(from, LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    private val vm by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
            LoginViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setUp()
        setUpWithEmail()
        val user =vm.firebaseAuth.currentUser

    }

    private fun signIn() {
        val signIntent: Intent = vm.getClient().signInIntent
        startActivityForResult(
            signIntent,
            RC_SIGN_IN
        )
    }

    private fun setUp() {
        login_with_google.setOnClickListener {
            signIn()
        }
    }

    //setup button with email
    private fun setUpWithEmail() {
        login.setOnClickListener {
            loginWithEmail()
        }
    }

    //authentication firebase with google
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)

        vm.firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                startActivity(MainActivity.getLaunchIntent(this))
            } else {
                Toast.makeText(this, "Google Sign In Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            startActivity(MainActivity.getLaunchIntent(this))
            finish()
        }
    }

    private fun loginWithEmail() {
        val email = email.text.toString()
        val password = passwordField.text.toString()

        if (clicked) login.setOnClickListener {
            Toast.makeText(applicationContext, "Mohon Tunggu...", Toast.LENGTH_LONG).show()
        }

        if (email.isNotEmpty() && password.isNotEmpty() && !clicked) {
            vm.firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful && !clicked) {
                    clicked = true
                    startActivity(Intent(this, MainActivity::class.java))
                    Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this, "Error registering, try again later ", Toast.LENGTH_LONG)
                        .show()
                }
            }
        } else {
            Toast.makeText(this, "Please fill up the Credentials ", Toast.LENGTH_LONG).show()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)

            } catch (e: ApiException) {
                e.printStackTrace()
                Toast.makeText(this, "Google Sign In Failed $e", Toast.LENGTH_LONG)
                    .show()
            }

        }
    }
}
