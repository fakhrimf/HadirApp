package com.hadir.hadirapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hadir.hadirapp.model.TeacherModel
import com.hadir.hadirapp.ui.register.RegisterFragment
import com.hadir.hadirapp.utils.MODEL_KEY

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val model = intent.getParcelableExtra<TeacherModel>(MODEL_KEY)
        val fragment = RegisterFragment()
        val bundle = Bundle()
        bundle.putParcelable(MODEL_KEY, model)
        fragment.arguments = bundle
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commitNow()
        }
    }
}
