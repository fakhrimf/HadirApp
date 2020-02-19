package com.hadir.hadirapp.ui.get_api

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.hadir.hadirapp.BuildConfig
import com.hadir.hadirapp.model.TeacherModel
import com.hadir.hadirapp.model.TeacherResponse
import com.hadir.hadirapp.ui.base.BaseAndroidViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetApiViewModel(application: Application): BaseAndroidViewModel(application) {
    val teacherModel by lazy {
        MutableLiveData<TeacherModel>()
    }

    val noResponse by lazy {
        MutableLiveData<Boolean>()
    }

    fun getData(grnrs: String) {
        val token = "Bearer ${BuildConfig.TOKEN}"
        val call = apiInterface.getProfile(grnrs, token)
        call.enqueue(object : Callback<TeacherResponse> {
            override fun onFailure(call: Call<TeacherResponse>, t: Throwable) {
                throw t
            }

            override fun onResponse(call: Call<TeacherResponse>, response: Response<TeacherResponse>) {
                val model = response.body()?.results
                if(model != null) {
                    model.alamatFull = "${model.alamatJalan}, ${model.alamatKecamatan}, ${model.alamatDesa}, ${model.alamatKota}"
                    teacherModel.value = model
                    noResponse.value = false
                } else {
                    noResponse.value = true
                }
            }
        })
    }
}