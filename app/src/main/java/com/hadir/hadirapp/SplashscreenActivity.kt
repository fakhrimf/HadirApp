package com.hadir.hadirapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hadir.hadirapp.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_splashscreen.*
import java.util.*

class SplashscreenActivity : AppCompatActivity() {
    val timerTask = object : TimerTask(){
        override fun run() {

            if (progressBar!!.progress < 100)
                progressBar!!.progress += 1
            else{
                timer?.cancel()
                val intent = Intent(baseContext, LoginActivity::class.java)
                startActivity(intent)
            }


        }
    }
    var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        initiateTimer()
    }

    fun initiateTimer() {
        timer = Timer()
        timer?.schedule(timerTask, 0, 20)
    }
}
