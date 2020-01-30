package com.hadir.hadirapp.utils

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hadir.hadirapp.model.DailyDataModel
import com.hadir.hadirapp.model.TeacherModel
import org.joda.time.DateTimeConstants.*
import org.joda.time.LocalDate
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*
import kotlin.collections.ArrayList

class TeacherRepository {

    private var listTeacher: MutableLiveData<ArrayList<TeacherModel>>

    init {
        listTeacher = getTeachersData()
    }

    fun getTeachersData(): MutableLiveData<ArrayList<TeacherModel>> {
        val database = FirebaseDatabase.getInstance().reference
        val list = ArrayList<TeacherModel>()
        val teacherList = MutableLiveData<ArrayList<TeacherModel>>()

        database.child(TEACHER_DATABASE_REF).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                throw Throwable("$p0")
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (i in p0.children) {
                    val model = i.getValue(TeacherModel::class.java)
                    model?.let {
                        it.id = i.key
                        list.add(it)
                    }
                }
                teacherList.value = list
            }
        })
        return teacherList
    }

    fun getTeachersData(name: String): MutableLiveData<ArrayList<TeacherModel>> {
        val database = FirebaseDatabase.getInstance().reference
        val list = ArrayList<TeacherModel>()
        val teacherList = MutableLiveData<ArrayList<TeacherModel>>()

        database.child(TEACHER_DATABASE_REF).orderByChild(TEACHER_NAME_DATABASE_REF).equalTo(name).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                throw Throwable("$p0")
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (i in p0.children) {
                    val model = i.getValue(TeacherModel::class.java)
                    model?.let {
                        it.id = i.key
                        list.add(it)
                    }
                    Log.d("TEACHER_DATA", "onDataChange: $model")
                }
                teacherList.value = list
            }
        })
        return teacherList
    }

    fun getTeacherDataByName(name: String): MutableLiveData<TeacherModel> {
        val database = FirebaseDatabase.getInstance().reference
        val teacher = MutableLiveData<TeacherModel>()

        database.child(TEACHER_DATABASE_REF).orderByChild(TEACHER_NAME_DATABASE_REF).equalTo(name).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                throw Throwable("$p0")
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (i in p0.children) {
                    val model = i.getValue(TeacherModel::class.java)
                    model?.let {
                        it.id = i.key
                    }
                    teacher.value = model
                    Log.d("TEACHER_DATA", "onDataChange: $model")
                }
            }
        })
        return teacher
    }

    fun getTeacherDataByKey(id: String, owner: LifecycleOwner): MutableLiveData<TeacherModel> {
        val model: MutableLiveData<TeacherModel> = MutableLiveData()
        listTeacher.observe(owner, androidx.lifecycle.Observer<ArrayList<TeacherModel>> {
            for (i in it) {
                if (i.rfid_key == id) model.value = i
            }
        })
        return model
    }

    fun getDailyData(owner: LifecycleOwner, date: Date): MutableLiveData<ArrayList<DailyDataModel>> {
        val database = FirebaseDatabase.getInstance().reference
        val list = ArrayList<DailyDataModel>()
        val teacherList = MutableLiveData<ArrayList<DailyDataModel>>()
        val dateString = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(date)
        Log.d("####", "getDailyData: $dateString")

        database.child(DAILY_DATABASE_REF).child(dateString).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                throw Throwable("$p0")
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (i in p0.children) {
                    val model = i.getValue(DailyDataModel::class.java)
                    model?.let {
                        listTeacher.observe(owner, androidx.lifecycle.Observer<ArrayList<TeacherModel>> { teacherModel ->
                            for (o in teacherModel) {
                                if (it.id == o.rfid_key) {
                                    it.teacherModel = o
                                    list.add(it)
                                }
                            }
                            teacherList.value = list
                        })
                    }
                }
            }
        })
        return teacherList
    }

    fun getDailyData(owner: LifecycleOwner, date: Date, key: String): MutableLiveData<ArrayList<DailyDataModel>> {
        val database = FirebaseDatabase.getInstance().reference
        val list = ArrayList<DailyDataModel>()
        val teacherList = MutableLiveData<ArrayList<DailyDataModel>>()
        val dateString = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(date)
        Log.d("####", "getDailyData: $dateString")

        database.child(DAILY_DATABASE_REF).child(dateString).orderByChild("id").equalTo(key).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                throw Throwable("$p0")
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (i in p0.children) {
                    val model = i.getValue(DailyDataModel::class.java)
                    model?.let {
                        listTeacher.observe(owner, androidx.lifecycle.Observer<ArrayList<TeacherModel>> { teacherModel ->
                            for (o in teacherModel) {
                                if (it.id == o.rfid_key) {
                                    it.teacherModel = o
                                    list.add(it)
                                }
                            }
                            teacherList.value = list
                        })
                    }
                }
            }
        })
        return teacherList
    }

    fun getPresentDataPerMonth(owner: LifecycleOwner, date: LocalDate) {
        var startMonth: LocalDate = date.dayOfMonth().withMinimumValue()
        val endMonth: LocalDate = date.dayOfMonth().withMaximumValue()
        val liveData = MutableLiveData<ArrayList<DailyDataModel>>()
        val list = ArrayList<DailyDataModel>()
        while (startMonth.isBefore(endMonth)) {
            val dateMilli = Date.from(Instant.ofEpochMilli(startMonth.toDateTimeAtStartOfDay().toInstant().millis))
            getDailyData(owner, dateMilli).observe(owner, androidx.lifecycle.Observer<ArrayList<DailyDataModel>> {
                for (i in it) {
                    list.add(i)
                    Log.d("####", "getPresentDataPerMonthByKey: ADDED $i")
                }
                liveData.value = list
                Log.d("####", "getPresentDataPerMonthByKey: DAILY DATA $it")
            })
            startMonth = startMonth.plusDays(1)
            Log.d("####", "getPresentDataPerMonthByKey: STARTMONTH = $startMonth")
        }
        liveData.observe(owner, androidx.lifecycle.Observer<ArrayList<DailyDataModel>> { dailyModel ->
            Log.d("####", "getPresentDataPerMonthByKey: LIST DATA $dailyModel")
        })
    }

    fun getPresentDataPerMonth(owner: LifecycleOwner, date: LocalDate, key: String) {
        var startMonth: LocalDate = date.dayOfMonth().withMinimumValue()
        val endMonth: LocalDate = date.dayOfMonth().withMaximumValue()
        val liveData = MutableLiveData<ArrayList<DailyDataModel>>()
        val list = ArrayList<DailyDataModel>()
        while (startMonth.isBefore(endMonth)) {
            val dateMilli = Date.from(Instant.ofEpochMilli(startMonth.toDateTimeAtStartOfDay().toInstant().millis))
            getDailyData(owner, dateMilli, "216886241240227144").observe(owner, androidx.lifecycle.Observer<ArrayList<DailyDataModel>> {
                for (i in it) {
                    list.add(i)
                    Log.d("####", "getPresentDataPerMonthByKey: ADDED $i")
                }
                liveData.value = list
                Log.d("####", "getPresentDataPerMonthByKey: DAILY DATA $it")
            })
            startMonth = startMonth.plusDays(1)
            Log.d("####", "getPresentDataPerMonthByKey: STARTMONTH = $startMonth")
        }
        liveData.observe(owner, androidx.lifecycle.Observer<ArrayList<DailyDataModel>> { dailyModel ->
            Log.d("####", "getPresentDataPerMonthByKey: LIST DATA $dailyModel")
        })
    }

    fun getPresentDataPerWeek(owner: LifecycleOwner, date: LocalDate) {
        var startWeek = date.withDayOfWeek(MONDAY)
        val endWeek = date.withDayOfWeek(SUNDAY)
        val list = ArrayList<DailyDataModel>()
        val liveData = MutableLiveData<ArrayList<DailyDataModel>>()
        while (startWeek.isBefore(endWeek)) {
            val dateMilli = Date.from(Instant.ofEpochMilli(startWeek.toDateTimeAtStartOfDay().toInstant().millis))
            getDailyData(owner, dateMilli, "216886241240227144").observe(owner, androidx.lifecycle.Observer<ArrayList<DailyDataModel>> {
                for (i in it) {
                    list.add(i)
                    Log.d("####", "getPresentDataPerWeekByKey: ADDED $i")
                }
                liveData.value = list
                Log.d("####", "getPresentDataPerWeekByKey: DAILY DATA $it")
            })
            Log.d("####", "getPresentDataPerWeekByKey: STARTWEEK = $startWeek")
            startWeek = startWeek.plusDays(1)
        }
    }

    fun getPresentDataPerWeek(owner: LifecycleOwner, date: LocalDate, key: String) {
        var startWeek = date.withDayOfWeek(MONDAY)
        val endWeek = date.withDayOfWeek(SUNDAY)
        val list = ArrayList<DailyDataModel>()
        val liveData = MutableLiveData<ArrayList<DailyDataModel>>()
        while (startWeek.isBefore(endWeek)) {
            val dateMilli = Date.from(Instant.ofEpochMilli(startWeek.toDateTimeAtStartOfDay().toInstant().millis))
            getDailyData(owner, dateMilli, key).observe(owner, androidx.lifecycle.Observer<ArrayList<DailyDataModel>> {
                for (i in it) {
                    list.add(i)
                    Log.d("####", "getPresentDataPerWeekByKey: ADDED $i")
                }
                liveData.value = list
                Log.d("####", "getPresentDataPerWeekByKey: DAILY DATA $it")
            })
            Log.d("####", "getPresentDataPerWeekByKey: STARTWEEK = $startWeek")
            startWeek = startWeek.plusDays(1)
        }
    }

    fun getPresentDataPerYear(owner: LifecycleOwner, date: LocalDate) {
        var startYear = date.withMonthOfYear(JANUARY).dayOfYear().withMinimumValue()
        val endWeek = date.withMonthOfYear(DECEMBER).dayOfYear().withMaximumValue()
        val list = ArrayList<DailyDataModel>()
        val liveData = MutableLiveData<ArrayList<DailyDataModel>>()
        while (startYear.isBefore(endWeek)) {
            val dateMilli = Date.from(Instant.ofEpochMilli(startYear.toDateTimeAtStartOfDay().toInstant().millis))
            getDailyData(owner, dateMilli, "216886241240227144").observe(owner, androidx.lifecycle.Observer<ArrayList<DailyDataModel>> {
                for (i in it) {
                    list.add(i)
                    Log.d("####", "getPresentDataPerWeekByKey: ADDED $i")
                }
                liveData.value = list
                Log.d("####", "getPresentDataPerWeekByKey: DAILY DATA $it")
            })
            Log.d("####", "getPresentDataPerWeekByKey: STARTYEAR = $startYear")
            startYear = startYear.plusDays(1)
        }
    }

    fun getPresentDataPerYear(owner: LifecycleOwner, date: LocalDate, key: String) {
        var startYear = date.withMonthOfYear(JANUARY).dayOfYear().withMinimumValue()
        val endWeek = date.withMonthOfYear(DECEMBER).dayOfYear().withMaximumValue()
        val list = ArrayList<DailyDataModel>()
        val liveData = MutableLiveData<ArrayList<DailyDataModel>>()
        while (startYear.isBefore(endWeek)) {
            val dateMilli = Date.from(Instant.ofEpochMilli(startYear.toDateTimeAtStartOfDay().toInstant().millis))
            getDailyData(owner, dateMilli, key).observe(owner, androidx.lifecycle.Observer<ArrayList<DailyDataModel>> {
                for (i in it) {
                    list.add(i)
                    Log.d("####", "getPresentDataPerWeekByKey: ADDED $i")
                }
                liveData.value = list
                Log.d("####", "getPresentDataPerWeekByKey: DAILY DATA $it")
            })
            Log.d("####", "getPresentDataPerWeekByKey: STARTYEAR = $startYear")
            startYear = startYear.plusDays(1)
        }
    }

    fun getPresentDataPerSemester(owner: LifecycleOwner, date: LocalDate, semester: Int) {
        var startYear = if (semester == 1) {
            date.withMonthOfYear(JANUARY).dayOfYear().withMinimumValue()
        } else {
            date.withMonthOfYear(JULY).dayOfYear().withMinimumValue()
        }
        val endWeek = if (semester == 1) {
            date.withMonthOfYear(JUNE).dayOfYear().withMaximumValue()
        } else {
            date.withMonthOfYear(DECEMBER).dayOfYear().withMaximumValue()
        }
        val list = ArrayList<DailyDataModel>()
        val liveData = MutableLiveData<ArrayList<DailyDataModel>>()
        while (startYear.isBefore(endWeek)) {
            val dateMilli = Date.from(Instant.ofEpochMilli(startYear.toDateTimeAtStartOfDay().toInstant().millis))
            getDailyData(owner, dateMilli).observe(owner, androidx.lifecycle.Observer<ArrayList<DailyDataModel>> {
                for (i in it) {
                    list.add(i)
                    Log.d("####", "getPresentDataPerWeekByKey: ADDED $i")
                }
                liveData.value = list
                Log.d("####", "getPresentDataPerWeekByKey: DAILY DATA $it")
            })
            Log.d("####", "getPresentDataPerWeekByKey: STARTYEAR = $startYear")
            startYear = startYear.plusDays(1)
        }
    }

    fun getPresentDataPerSemester(owner: LifecycleOwner, date: LocalDate, semester: Int, key: String) {
        var startYear = if (semester == 1) {
            date.withMonthOfYear(JANUARY).dayOfYear().withMinimumValue()
        } else {
            date.withMonthOfYear(JULY).dayOfYear().withMinimumValue()
        }
        val endWeek = if (semester == 1) {
            date.withMonthOfYear(JUNE).dayOfYear().withMaximumValue()
        } else {
            date.withMonthOfYear(DECEMBER).dayOfYear().withMaximumValue()
        }
        val list = ArrayList<DailyDataModel>()
        val liveData = MutableLiveData<ArrayList<DailyDataModel>>()
        while (startYear.isBefore(endWeek)) {
            val dateMilli = Date.from(Instant.ofEpochMilli(startYear.toDateTimeAtStartOfDay().toInstant().millis))
            getDailyData(owner, dateMilli, key).observe(owner, androidx.lifecycle.Observer<ArrayList<DailyDataModel>> {
                for (i in it) {
                    list.add(i)
                    Log.d("####", "getPresentDataPerWeekByKey: ADDED $i")
                }
                liveData.value = list
                Log.d("####", "getPresentDataPerWeekByKey: DAILY DATA $it")
            })
            Log.d("####", "getPresentDataPerWeekByKey: STARTYEAR = $startYear")
            startYear = startYear.plusDays(1)
        }
    }

    companion object {
        fun newInstance() = TeacherRepository()
    }
}