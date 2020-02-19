package com.hadir.hadirapp.ui.get_api

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hadir.hadirapp.R
import com.hadir.hadirapp.RegisterActivity
import com.hadir.hadirapp.ui.base.BaseFragment
import com.hadir.hadirapp.utils.MODEL_KEY
import kotlinx.android.synthetic.main.fragment_get_api_data.*

class GetApiFragment : BaseFragment() {
    private val vm by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(GetApiViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_get_api_data, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        findBtn.setOnClickListener {
            if(checkEmpty()) {
                circularProgress.visibility = View.VISIBLE
                vm.getData(grnrsField.text.toString())
                vm.teacherModel.observe(viewLifecycleOwner, Observer {
                    vm.noResponse.observe(viewLifecycleOwner, Observer { noResponse ->
                        if(!noResponse) {
                            makeText(it.name)
                            circularProgress.visibility = View.INVISIBLE
                            val intent = Intent(requireContext(), RegisterActivity::class.java)
                            intent.putExtra(MODEL_KEY, it)
                        } else {
                            circularProgress.visibility = View.INVISIBLE
                            tv_header.text = getString(R.string.no_grnrs_found)
                        }
                    })
                })
            }
        }
        login_here.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun checkEmpty(): Boolean {
        if(grnrsField.text.isNullOrBlank()) {
            grnrsField.error = getString(R.string.grnrs_error)
            return false
        }
        return true
    }
}
