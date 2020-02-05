package com.hadir.hadirapp.model

class TeacherData {

    fun getTeacherData(): ArrayList<TeacherModel> {
        val list = ArrayList<TeacherModel>()
        for (i in 0 until 5) {
            list.add(TeacherModel("$i", "Test $i", "user$i", "pass$i", "$i"))
        }
        return list
    }

}