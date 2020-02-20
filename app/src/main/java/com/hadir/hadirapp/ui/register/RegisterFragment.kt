package com.hadir.hadirapp.ui.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hadir.hadirapp.R
import com.hadir.hadirapp.model.TeacherModel
import com.hadir.hadirapp.utils.MODEL_KEY
import kotlinx.android.synthetic.main.fragment_login.*

class RegisterFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val model = arguments?.get(MODEL_KEY) as TeacherModel?
        if (model != null) tv_header.text = getString(R.string.create_an_account) + " for ${model.name!!.split(" ")[1]}"
    }
}
