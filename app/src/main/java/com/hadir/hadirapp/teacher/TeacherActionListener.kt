package com.hadir.hadirapp.teacher

import com.hadir.hadirapp.model.TeacherModel

interface TeacherUserActionListener {
    fun onClickItem(teacherModel: TeacherModel)
}