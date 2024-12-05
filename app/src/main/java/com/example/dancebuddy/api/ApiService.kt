package com.example.dancebuddy.api

import com.example.dancebuddy.coredata.DanceClass
import com.example.dancebuddy.coredata.Event
import com.example.dancebuddy.coredata.user.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("dance_class/")
    fun getDanceClasses(): Call<List<DanceClass>>

    @GET("event/")
    fun getEvents(): Call<List<Event>>

    @GET("user/")
    fun getUsers(): Call<List<User>>

    @GET("user/{pk}/")
    fun getUserById(@Path("pk") pk: Int): Call<User>

    @POST("token/")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}