package com.hadir.hadirapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        register_here.setOnClickListener { startActivity(Intent(this, RegisterActivity::class.java)) }
        loginButton.setOnClickListener { startActivity(Intent(this, StatisticsActivity::class.java)) }
    }
}
