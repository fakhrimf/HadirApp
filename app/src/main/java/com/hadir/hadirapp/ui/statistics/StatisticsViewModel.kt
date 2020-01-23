package com.hadir.hadirapp.ui.statistics

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.database.FirebaseDatabase
import com.hadir.hadirapp.model.TeacherModel

class StatisticsViewModel(application: Application) : AndroidViewModel(application) {
    val context by lazy {
        getApplication() as Context
    }

    fun getTeachersData() {
        val database = FirebaseDatabase.getInstance().reference
        val teachersList = ArrayList<TeacherModel>()
    }
}
