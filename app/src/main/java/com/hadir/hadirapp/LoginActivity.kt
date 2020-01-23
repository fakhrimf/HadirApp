package com.hadir.hadirapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.hadir.hadirapp.ui.base.BaseActivity
import com.hadir.hadirapp.ui.login.LoginFragment
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        addFragment(LoginFragment())
    }
}
