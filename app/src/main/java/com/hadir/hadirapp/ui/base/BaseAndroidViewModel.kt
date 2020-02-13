package com.hadir.hadirapp.ui.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import com.hadir.hadirapp.utils.TeacherRepository
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
}