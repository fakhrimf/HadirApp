package com.hadir.hadirapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hadir.hadirapp.R
import com.hadir.hadirapp.model.DailyDataModel
import com.hadir.hadirapp.teacher.TeacherVM
import com.hadir.hadirapp.ui.home.adapter.DailyAdapter
import com.hadir.hadirapp.ui.home.listener.HomeActionListener
import com.hadir.hadirapp.utils.TeacherRepository
import kotlinx.android.synthetic.main.fragment_home.*
import org.joda.time.LocalDate

class HomeFragment : Fragment(), HomeActionListener {
    private lateinit var teacherVM: TeacherVM
    private lateinit var teacherRepository: TeacherRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        teacherVM = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
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

    override fun onClickItem(dailyDataModel: DailyDataModel) {
        Toast.makeText(context,"${dailyDataModel.teacherModel?.name}", Toast.LENGTH_LONG).show()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshHome.isRefreshing = true
        setRecycler()
        swipeRefreshHome.setOnRefreshListener {
            setRecycler()
        }
    }

    private fun setRecycler() {
        teacherRepository.getPresentDataPerYear(viewLifecycleOwner, LocalDate()).observe(this, Observer {
            val dailyAdapter = DailyAdapter(it, this)
            Log.d("%%%%", "$it")
            swipeRefreshHome.isRefreshing = false
            rv_teacher.apply {
                adapter = dailyAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        })
    }
}