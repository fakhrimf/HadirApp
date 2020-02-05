package com.hadir.hadirapp.ui.statistics

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hadir.hadirapp.R
import com.hadir.hadirapp.model.DailyDataModel
import com.hadir.hadirapp.model.TeacherModel
import org.joda.time.LocalDate
import java.util.*

class StatisticsFragment : Fragment() {
    private val vm by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(StatisticsViewModel::class.java)
    }

    companion object {
        fun newInstance() = StatisticsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.statistics_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("TEACHER_LIST", "onActivityCreated: activity created.")
        vm.getTeachersData().observe(viewLifecycleOwner, Observer<ArrayList<TeacherModel>> {
            Log.d("TEACHER_LIST", "onActivityCreated: $it")
        })
        vm.getDailyData(viewLifecycleOwner, Date()).observe(viewLifecycleOwner, Observer<ArrayList<DailyDataModel>> {
            Log.d("TEACHER_LIST_DAILY", "onActivityCreated: $it")
        })
        vm.getPresentDataPerYear(viewLifecycleOwner, LocalDate(Date()))
    }
}
