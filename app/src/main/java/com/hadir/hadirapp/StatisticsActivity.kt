package com.hadir.hadirapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hadir.hadirapp.ui.statistics.StatisticsFragment

class StatisticsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.statistics_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, StatisticsFragment.newInstance())
                .commitNow()
        }
    }
}
