package com.hadir.hadirapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hadir.hadirapp.GetApiActivity
import com.hadir.hadirapp.HomeActivity
import com.hadir.hadirapp.R
import com.hadir.hadirapp.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment() {

    private val vm by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(LoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val username = vm.sharedPreferenceUtils.getUsername()
        val password = vm.sharedPreferenceUtils.getPassword()
        val remember = vm.sharedPreferenceUtils.getRememberMe()
        if (username != null && password != null) {
            email.setText(username)
            passwordField.setText(password)
            circularProgress.visibility = View.VISIBLE
            makeText(getString(R.string.auto_login))
            val isRight = vm.login(email.text.toString(), passwordField.text.toString(), viewLifecycleOwner)
            isRight.observe(viewLifecycleOwner, Observer {
                val intent = Intent(requireContext(), HomeActivity::class.java)
                if (it) {
                    startActivity(intent)
                } else if (!it) {
                    tv_header.text = getString(R.string.session_expired)
                    circularProgress.visibility = View.INVISIBLE
                }
            })
        }

        loginBtn.setOnClickListener {
            if (checkEmpty()) {
                circularProgress.visibility = View.VISIBLE
                makeText(getString(R.string.logging_in))
                val isRight = vm.login(email.text.toString(), passwordField.text.toString(), viewLifecycleOwner)
                isRight.observe(viewLifecycleOwner, Observer {
                    val intent = Intent(requireContext(), HomeActivity::class.java)
                    if (it) {
                        startActivity(intent)
                        requireActivity().finish()
                    } else if (!it) {
                        tv_header.text = getString(R.string.username_password_error)
                        circularProgress.visibility = View.INVISIBLE
                    }
                })
            }
        }

        remember_me.setOnClickListener {
            if (!checkBox.isChecked) {
                checkBox.isChecked = true
                vm.sharedPreferenceUtils.setRememberMe(true)
            } else {
                checkBox.isChecked = false
                vm.sharedPreferenceUtils.setRememberMe(false)
            }
        }

        register.setOnClickListener {
            val intent = Intent(requireContext(), GetApiActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkEmpty(): Boolean {
        var isNotEmpty = true
        if (email.text.isNullOrBlank()) {
            email.error = getString(R.string.username_empty)
            isNotEmpty = false
        }
        if (passwordField.text.isNullOrBlank()) {
            passwordField.error = getString(R.string.password_empty)
            isNotEmpty = false
        }
        return isNotEmpty
    }
}
