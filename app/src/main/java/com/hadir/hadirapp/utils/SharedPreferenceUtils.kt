package com.hadir.hadirapp.utils

import android.content.Context

class SharedPreferenceUtils(private val context: Context) {
    fun getSharedPreferences() = context.getSharedPreferences(SP_KEY, Context.MODE_PRIVATE)
    fun getUsername() = getSharedPreferences().getString(USERNAME_SP_KEY, null)
    fun getPassword() = getSharedPreferences().getString(PASSWORD_SP_KEY, null)
    fun setUsername(username: String) {
        with(getSharedPreferences().edit()) {
            putString(USERNAME_SP_KEY, username)
            commit()
        }
    }
    fun setPassword(password: String) {
        with(getSharedPreferences().edit()) {
            putString(PASSWORD_SP_KEY, password)
            commit()
        }
    }

    companion object {
        fun newInstance(context: Context) = SharedPreferenceUtils(context)
    }
}