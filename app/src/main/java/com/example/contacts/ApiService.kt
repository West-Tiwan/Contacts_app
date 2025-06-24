package com.example.contacts

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiService {
    val api: RandomUserInterface by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl("https://randomuser.me/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}