package com.hadir.hadirapp.ui.login

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.hadir.hadirapp.R
import com.hadir.hadirapp.model.TeacherModel
import com.hadir.hadirapp.ui.base.BaseAndroidViewModel

class LoginViewModel(application: Application) : BaseAndroidViewModel(application) {
    val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val googleSignInOptions: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    fun getClient(): GoogleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)

    fun login(username: String, password: String, owner: LifecycleOwner) : MutableLiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        getTeachersData().observe(owner, Observer {
            for(i in it) {
                if(i.username == username && i.password == password) {
                    sharedPreferenceUtils.setUsername(username)
                    sharedPreferenceUtils.setPassword(password)
                    liveData.value = true
                } else {
                    liveData.value = false
                }
            }
        })
        return liveData
    }
}