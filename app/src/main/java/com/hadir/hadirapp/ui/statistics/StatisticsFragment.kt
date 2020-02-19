package com.hadir.hadirapp.ui.statistics

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hadir.hadirapp.R
import com.hadir.hadirapp.model.DailyDataModel
import com.hadir.hadirapp.model.TeacherModel
import com.hadir.hadirapp.utils.TeacherRepository
import kotlinx.android.synthetic.main.statistics_fragment.*
import org.joda.time.LocalDate
import java.util.*

class StatisticsFragment : Fragment() {
    private val vm by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(StatisticsViewModel::class.java)
    }

    private val NumKehInMonth = 14
    private val NumKehInWeek = 4
    private val NumIz = 0
    private val NumSak = 0
    private val curDate = LocalDate.now()

    companion object {
        fun newInstance() = StatisticsFragment()
    }

    fun countPercent() {
        val spin_items = arrayOf("Per Week", "Per Month")
        var arAdpt = ArrayAdapter(requireActivity(), R.layout.spin_items, spin_items)
        arAdpt.setDropDownViewResource(R.layout.spin_dropdown)
        spin_stat.adapter = arAdpt

        spin_stat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedItem = p0?.getItemAtPosition(p2).toString()
                if (selectedItem == "Per Week"){
                    var totalWorkWeek = TeacherRepository().getWorkDayPerWeek(curDate)
                    var percentage = (NumKehInWeek/totalWorkWeek) * 100
                    circularProgress.apply {
                        setProgressWithAnimation(percentage.toFloat(), 2000)
                    }
                    tvPersentage.setText(percentage.toString())
                    text_statistic.setText("Statistics This Week")
                }
                if (selectedItem == "Per Month") {
                    var totalWorkMonth = TeacherRepository().getWorkDayPerMonth(curDate)
                    var percentage = (NumKehInMonth/totalWorkMonth) * 100
                    circularProgress.apply {
                        setProgressWithAnimation(percentage.toFloat(), 2000)
                    }
                    tvPersentage.setText(percentage.toString())
                    text_statistic.setText("Statistics This Month")

                }else{}
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        angka_kehadiran.setText(NumSak.toString())
        angka_izin.setText(NumIz.toString())
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
        vm.getPresentDataPerYear(viewLifecycleOwner, LocalDate(Date())).observe(viewLifecycleOwner, Observer<ArrayList<DailyDataModel>> {
            Log.d("TEACHER_YEARLY", "onActivityCreated: $it")
        })
        countPercent()
    }
}
