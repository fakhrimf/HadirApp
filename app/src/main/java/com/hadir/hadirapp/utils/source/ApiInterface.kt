package com.hadir.hadirapp.utils.source

import retrofit2.http.GET

interface ApiInterface {
    //Get Teacher Data
    // TODO: 13/02/2020 Cari tau info API
    @GET("api/v2/profile")
    fun getProfile()
}