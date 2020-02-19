package com.hadir.hadirapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.hadir.hadirapp.ui.base.BaseActivity
import com.hadir.hadirapp.ui.login.LoginFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logout.setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        startActivity(LoginFragment.getLaunchIntent(this))
        FirebaseAuth.getInstance().signOut()
    }

}
