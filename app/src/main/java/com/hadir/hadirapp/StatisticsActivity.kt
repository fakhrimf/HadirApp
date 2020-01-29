package com.hadir.hadirapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
