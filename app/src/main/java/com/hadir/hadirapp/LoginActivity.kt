package com.hadir.hadirapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var TextView : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        TextView = findViewById(R.id.register_here)
        TextView.setOnClickListener { startActivity(Intent(this, RegisterActivity::class.java)) }
        loginBtn.setOnClickListener { startActivity(Intent(this, HomeActivity::class.java)) }
    }
}
