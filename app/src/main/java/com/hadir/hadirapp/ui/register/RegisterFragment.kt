package com.hadir.hadirapp.ui.register

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hadir.hadirapp.LoginActivity
import com.hadir.hadirapp.R
import com.hadir.hadirapp.model.TeacherModel
import com.hadir.hadirapp.ui.base.BaseFragment
import com.hadir.hadirapp.utils.IMAGE_REQUEST_CODE
import com.hadir.hadirapp.utils.MODEL_KEY
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : BaseFragment() {
    private val vm by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(RegisterViewModel::class.java)
    }

    private lateinit var path: Uri
    private var bitmap: Bitmap? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val model = arguments?.get(MODEL_KEY) as TeacherModel?
        if (model != null) tv_header.text = getString(R.string.create_an_account) + " for ${model.name!!.split(" ")[0]}"
        imageIconBtn.setOnClickListener {
            startActivityForResult(vm.getImageIntent(), IMAGE_REQUEST_CODE)
        }
        editIconBtn.setOnClickListener{
            startActivityForResult(vm.getImageIntent(), IMAGE_REQUEST_CODE)
        }
        registerBtn.setOnClickListener{
            addTeacher()
        }
    }

    private fun addTeacher() {
        val username = usernameField.text.toString()
        val password = passwordField.text.toString()
        if(checkEmpty()) {
            circularProgress.visibility = View.VISIBLE
            val model = arguments?.get(MODEL_KEY) as TeacherModel
            model.username = username
            model.password = password
            val add = vm.addTeacher(model, path)
            add.observe(viewLifecycleOwner, Observer {
                circularProgress.visibility = View.INVISIBLE
                val intent = Intent(context, LoginActivity::class.java)
                vm.sharedPreferenceUtils.setUsername(username)
                vm.sharedPreferenceUtils.setPassword(password)
                startActivity(intent)
                requireActivity().finish()
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMAGE_REQUEST_CODE && data != null && data.data != null) {
            path = data.data!!
            bitmap = vm.getImageBitmap(requireActivity().contentResolver, path)
            ivProfile.setImageBitmap(bitmap)
        }
    }

    private fun checkEmpty(): Boolean {
        var isNotEmpty = true
        if(usernameField.text.isNullOrBlank()) {
            usernameField.error = getString(R.string.username_empty)
            isNotEmpty = false
        }
        if(passwordField.text.isNullOrBlank()) {
            passwordField.error = getString(R.string.password_empty)
            isNotEmpty = false
        }
        if(bitmap == null) {
            makeText(getString(R.string.dp_empty))
            isNotEmpty = false
        }
        return isNotEmpty
    }
}
