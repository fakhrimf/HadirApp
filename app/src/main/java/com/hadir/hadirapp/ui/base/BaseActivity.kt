package com.hadir.hadirapp.ui.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.hadir.hadirapp.R
import com.hadir.hadirapp.ui.login.LoginFragment
import com.hadir.hadirapp.ui.register.RegisterFragment

abstract class BaseActivity : AppCompatActivity() {
    private lateinit var mActivity: AppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        mActivity = this
    }

    fun addFragment(fragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment, fragment.javaClass.simpleName)
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.backStackEntryCount
        val state = supportFragmentManager.findFragmentById(R.id.container)
        if (fragment == 1) {
            finish()
        } else {
            if (state is RegisterFragment
            )
                finish()
            else if (state is LoginFragment
            )
                System.exit(0)
            else
                super.onBackPressed()
        }
    }
}