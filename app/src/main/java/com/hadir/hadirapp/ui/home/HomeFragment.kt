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
import com.hadir.hadirapp.ui.home.adapter.DailyAdapter
import com.hadir.hadirapp.ui.home.listener.HomeActionListener
import kotlinx.android.synthetic.main.fragment_home.*
import org.joda.time.LocalDate
import java.util.*

class HomeFragment : Fragment(), HomeActionListener {
    private val vm by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(HomeViewModel::class.java)
    }

    companion object {
        fun newInstance() = HomeFragment()
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
        vm.getCurrentUser(viewLifecycleOwner).observe(viewLifecycleOwner, Observer { teacher ->
            tv_name.text = teacher.name
            vm.getDailyData(viewLifecycleOwner, Date()).observe(viewLifecycleOwner, Observer {
                val dailyAdapter = DailyAdapter(it, this)
                Log.d("%%%%", "$it")
                swipeRefreshHome.isRefreshing = false
                rv_teacher.apply {
                    adapter = dailyAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                }
            })
        })
    }
}