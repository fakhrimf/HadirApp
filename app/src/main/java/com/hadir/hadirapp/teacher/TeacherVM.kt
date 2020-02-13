package com.hadir.hadirapp.teacher

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.hadir.hadirapp.model.TeacherModel

class TeacherVM(application: Application) : AndroidViewModel(application) {
    val context = getApplication() as Context
    private var isLoaded = false

    val teacherList: MutableLiveData<ArrayList<TeacherModel>> by lazy {
        MutableLiveData<ArrayList<TeacherModel>>()
    }

    fun getIsLoaded(): Boolean {
        return isLoaded
    }

    fun setIsLoaded(isLoaded: Boolean) {
        this.isLoaded = isLoaded
    }


}

