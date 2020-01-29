package com.hadir.hadirapp.login

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
import com.hadir.hadirapp.MainActivity
import com.hadir.hadirapp.R
import com.hadir.hadirapp.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

companion object {
    val RC_SIGN_IN: Int = 1
    private lateinit var firebaseAuth:FirebaseAuth
    private lateinit var TextView: TextView
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions
}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        TextView = findViewById(
            R.id.register_here
        )
        TextView.setOnClickListener { startActivity(Intent(this, RegisterActivity::class.java)) }
        firebaseAuth = FirebaseAuth.getInstance()
        configureGoogleSignIn()
        setUp()

    }
    private  fun configureGoogleSignIn(){
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this,
            mGoogleSignInOptions
        )
    }

    private  fun signIn(){
        val signIntent:Intent= mGoogleSignInClient.signInIntent
        startActivityForResult(signIntent,
            RC_SIGN_IN
        )
    }

    private  fun setUp(){
        login_with_google.setOnClickListener {
            signIn()
        }
    }

    private  fun firebaseAuthWithGoogle(acct:GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(acct.idToken,null)

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful){
                startActivity(MainActivity.getLaunchIntent(this))
            }else{
                Toast.makeText(this,"Google Sign In Failed",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val  user = FirebaseAuth.getInstance().currentUser
        if(user != null){
            startActivity(MainActivity.getLaunchIntent(this))
            finish()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN){
            val task : Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)

            try{
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)

            }catch (e:ApiException){
                e.printStackTrace()
                Toast.makeText(this,"Google Sign In Failed"+ e,Toast.LENGTH_LONG)
                    .show()

            }

        }
    }
}
