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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TeacherRepository {

    private var listTeacher:MutableLiveData<ArrayList<TeacherModel>>

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

    fun getDailyData(owner: LifecycleOwner): MutableLiveData<ArrayList<DailyDataModel>> {
        val database = FirebaseDatabase.getInstance().reference
        val list = ArrayList<DailyDataModel>()
        val teacherList = MutableLiveData<ArrayList<DailyDataModel>>()
        val date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

        database.child(DAILY_DATABASE_REF).child(date).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                throw Throwable("$p0")
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (i in p0.children) {
                    val model = i.getValue(DailyDataModel::class.java)
                    model?.let {
                        listTeacher.observe(owner, androidx.lifecycle.Observer<ArrayList<TeacherModel>> { teacherModel ->
                            for(o in teacherModel) {
                                if(it.id == o.rfid_key) {
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

    companion object {
        fun newInstance() = TeacherRepository()
    }
}