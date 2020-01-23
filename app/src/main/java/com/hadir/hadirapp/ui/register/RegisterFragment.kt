package com.hadir.hadirapp.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hadir.hadirapp.LoginActivity
import com.hadir.hadirapp.R
import com.hadir.hadirapp.model.TeacherModel
import com.hadir.hadirapp.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : BaseFragment() {
    private val vm by lazy {
    }

    companion object {
        fun newInstance() = RegisterFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loginButton.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)

        }


    }
}
