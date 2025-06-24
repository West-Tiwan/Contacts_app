package com.example.contacts

import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserInterface {
    @GET("api/")
    suspend fun getUsers(@Query("results") count: Int = 20): UserResponse
}