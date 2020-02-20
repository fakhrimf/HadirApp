package com.hadir.hadirapp.utils

import android.content.Context

class SharedPreferenceUtils(private val context: Context) {
    fun getSharedPreferences() = context.getSharedPreferences(SP_KEY, Context.MODE_PRIVATE)
    fun getUsername() = getSharedPreferences().getString(USERNAME_SP_KEY, null)
    fun getPassword() = getSharedPreferences().getString(PASSWORD_SP_KEY, null)
    fun getRememberMe() = getSharedPreferences().getBoolean(REMEMBER_ME_SP_KEY, false)
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
    fun setRememberMe(remember: Boolean) {
        with(getSharedPreferences().edit()) {
            putBoolean(REMEMBER_ME_SP_KEY, remember)
            commit()
        }
    }

    companion object {
        fun newInstance(context: Context) = SharedPreferenceUtils(context)
    }
}