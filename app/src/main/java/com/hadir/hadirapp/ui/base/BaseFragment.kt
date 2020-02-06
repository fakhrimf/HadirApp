package com.hadir.hadirapp.ui.base

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.hadir.hadirapp.R

abstract class BaseFragment : Fragment() {

    protected fun Context.showToast(message: String) {
        try {
            if (message.contains("HTTP") || message.contains("Unexpected") ||
                message.contains("setLenient") || message.contains("0")
            ) {
                Toast.makeText(
                    applicationContext,
                    "Coba Lagi",
                    Toast.LENGTH_LONG
                ).show()
            } else if (message.contains("null"))
                Log.e("DATA", message)
            else
                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected inline fun <reified act> startActivity(context: Context) {
        val intent = Intent(context, act::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    protected fun showSnackBar(message: String?, showingView: View) {
        Snackbar.make(showingView, message.toString(), Snackbar.LENGTH_LONG).show()
    }

}
