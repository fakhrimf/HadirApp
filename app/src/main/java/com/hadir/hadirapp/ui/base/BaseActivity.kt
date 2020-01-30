package com.hadir.hadirapp.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.hadir.hadirapp.R
import com.hadir.hadirapp.ui.login.LoginFragment
import com.hadir.hadirapp.ui.register.RegisterFragment
import net.danlew.android.joda.JodaTimeAndroid
import kotlin.system.exitProcess

abstract class BaseActivity : AppCompatActivity() {
    private lateinit var mActivity: AppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        mActivity = this
        JodaTimeAndroid.init(this)
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
            when (state) {
                is RegisterFragment -> finish()
                is LoginFragment -> exitProcess(0)
                else -> super.onBackPressed()
            }
        }
    }
}