package com.hadir.hadirapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hadir.hadirapp.R
import com.hadir.hadirapp.model.TeacherData
import com.hadir.hadirapp.model.TeacherModel
import com.hadir.hadirapp.teacher.TeacherAdapter
import com.hadir.hadirapp.teacher.TeacherUserActionListener
import com.hadir.hadirapp.teacher.TeacherVM
import com.hadir.hadirapp.utils.TeacherRepository
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), TeacherUserActionListener {
    private lateinit var teacherVM: TeacherVM
    private lateinit var teacherRepository: TeacherRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        teacherVM = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(activity!!.application)
        ).get(TeacherVM::class.java)
        teacherRepository = TeacherRepository()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onClickItem(teacherModel: TeacherModel) {
        Toast.makeText(context, teacherModel.name, Toast.LENGTH_LONG).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teacherRepository.getTeachersData().observe(this, Observer {
            val adapter = TeacherAdapter(it, this)
            rv_teacher.adapter = adapter
        })
        rv_teacher.layoutManager = LinearLayoutManager(view.context)
        rv_teacher.setHasFixedSize(true)


    }

}