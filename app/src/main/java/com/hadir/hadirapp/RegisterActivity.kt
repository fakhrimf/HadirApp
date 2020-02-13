package com.hadir.hadirapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class RegisterActivity : AppCompatActivity() {

    private lateinit var TextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        TextView = findViewById(R.id.login_here)
        TextView.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
