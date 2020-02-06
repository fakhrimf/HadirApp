package com.hadir.hadirapp.login

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.hadir.hadirapp.R

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    val context by lazy {
        getApplication() as Context
    }

    private val googleSignInOptions: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    fun getClient(): GoogleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)
}