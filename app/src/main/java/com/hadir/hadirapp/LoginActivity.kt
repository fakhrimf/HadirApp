package com.hadir.hadirapp

import android.os.Bundle
import com.hadir.hadirapp.ui.base.BaseActivity
import com.hadir.hadirapp.ui.login.LoginFragment

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        addFragment(LoginFragment())
    }
}
