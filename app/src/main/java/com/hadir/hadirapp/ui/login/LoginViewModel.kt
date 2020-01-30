package com.hadir.hadirapp.ui.login

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.hadir.hadirapp.R
import com.hadir.hadirapp.model.TeacherModel

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    companion object{
        val RC_SIGN_IN: Int = 1
    }

    private  val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val context by lazy{
        getApplication() as Context
    }


    private val mDatabase by lazy{
    }

    private val googleSignInOptions: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }


    private val teacherList by lazy {
        MutableLiveData<ArrayList<TeacherModel>>()
    }





}