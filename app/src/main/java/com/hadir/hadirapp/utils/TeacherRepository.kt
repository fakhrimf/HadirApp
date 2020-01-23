package com.hadir.hadirapp.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hadir.hadirapp.model.TeacherModel

class TeacherRepository {

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

    companion object {
        fun newInstance() = TeacherRepository()
    }
}