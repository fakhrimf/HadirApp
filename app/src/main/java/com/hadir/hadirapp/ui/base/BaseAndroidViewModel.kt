package com.hadir.hadirapp.ui.base

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.hadir.hadirapp.model.TeacherModel
import com.hadir.hadirapp.utils.SharedPreferenceUtils
import com.hadir.hadirapp.utils.TeacherRepository
import com.hadir.hadirapp.utils.source.ApiClient
import com.hadir.hadirapp.utils.source.ApiInterface
import org.joda.time.LocalDate
import java.util.*

// BoilerPlate
open class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {
    val context by lazy {
        getApplication() as Context
    }
    val repo by lazy {
        TeacherRepository.newInstance()
    }
    val sharedPreferenceUtils by lazy {
        SharedPreferenceUtils.newInstance(context)
    }
    val apiClient by lazy {
        ApiClient.getClient()
    }
    val apiInterface by lazy {
        apiClient.create(ApiInterface::class.java)
    }

    fun getCurrentUser(owner: LifecycleOwner) : MutableLiveData<TeacherModel> {
        val liveData = MutableLiveData<TeacherModel>()
        val username = sharedPreferenceUtils.getUsername()
        getTeachersData().observe(owner, androidx.lifecycle.Observer {
            for (i in it) {
                if(i.username == username){
                    liveData.value = i
                }
            }
        })
        return liveData
    }

    fun makeText(text: String) = Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    fun getTeachersData() = repo.getTeachersData()
    fun getDailyData(owner: LifecycleOwner, date: Date) = repo.getDailyData(owner, date)
    fun getDailyData(owner: LifecycleOwner, date: Date, key: String) = repo.getDailyData(owner, date, key)
    fun getPresentDataPerWeek(owner: LifecycleOwner, date: LocalDate) = repo.getPresentDataPerWeek(owner, date)
    fun getPresentDataPerWeek(owner: LifecycleOwner, date: LocalDate, key: String) = repo.getPresentDataPerWeek(owner, date, key)
    fun getPresentDataPerMonth(owner: LifecycleOwner, date: LocalDate) = repo.getPresentDataPerMonth(owner, date)
    fun getPresentDataPerMonth(owner: LifecycleOwner, date: LocalDate, key: String) = repo.getPresentDataPerMonth(owner, date, key)
    fun getPresentDataPerSemester(owner: LifecycleOwner, date: LocalDate, key: String, semester:Int) = repo.getPresentDataPerSemester(owner, date, semester, key)
    fun getPresentDataPerSemester(owner: LifecycleOwner, date: LocalDate, semester: Int) = repo.getPresentDataPerSemester(owner, date, semester)
    fun getPresentDataPerYear(owner: LifecycleOwner, date: LocalDate) = repo.getPresentDataPerYear(owner, date)
    fun getPresentDataPerYear(owner: LifecycleOwner, date: LocalDate, key: String) = repo.getPresentDataPerYear(owner, date, key)
    fun getPresentInfoPerWeek(owner: LifecycleOwner, date: LocalDate, key: String) = repo.getPresentInfoPerWeek(owner, date, key)
    fun getPresentInfoPerMonth(owner: LifecycleOwner, date: LocalDate, key: String) = repo.getPresentInfoPerMonth(owner, date, key)
    fun getPresentInfoPerSemester(owner: LifecycleOwner, date: LocalDate, semester: Int, key: String) = repo.getPresentInfoPerSemester(owner, date, semester, key)
    fun getPresentInfoPerYear(owner: LifecycleOwner, date: LocalDate, key: String) = repo.getPresentInfoPerYear(owner, date, key)
    fun getWorkDayPerWeek(date: LocalDate) = repo.getWorkDayPerWeek(date)
    fun getWorkDayPerMonth(date: LocalDate) = repo.getWorkDayPerMonth(date)
    fun getWorkDayPerYear(date: LocalDate) = repo.getWorkDayPerYear(date)
}