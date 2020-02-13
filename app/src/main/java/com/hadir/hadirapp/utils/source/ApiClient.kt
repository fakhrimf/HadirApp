package com.hadir.hadirapp.utils.source

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    fun getClient(): Retrofit {
        val baseUrl = "http://api.smkn4bdg.sch.id:88/"
        return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
    }
}