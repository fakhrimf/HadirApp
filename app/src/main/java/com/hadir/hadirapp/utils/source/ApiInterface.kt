package com.hadir.hadirapp.utils.source

import com.hadir.hadirapp.model.TeacherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiInterface {
    //Get Teacher Data
    @GET("api/v2/profile/guru/{grnrs}")
    fun getProfile(@Path("grnrs") grnrs: String, @Header("Authorization") token: String) : Call<TeacherResponse>
    // TODO: 19/02/2020 Get Jadwal guru
}