package com.hadir.hadirapp.ui.statistics

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.hadir.hadirapp.utils.TeacherRepository

class StatisticsViewModel(application: Application) : AndroidViewModel(application) {
    private val context by lazy {
        getApplication() as Context
    }
    private val repo by lazy {
        TeacherRepository.newInstance()
    }

    fun getTeachersData() = repo.getTeachersData()
}
