package com.example.dancebuddy.api

import com.example.dancebuddy.coredata.DanceClass
import com.example.dancebuddy.coredata.DancerProfile
import com.example.dancebuddy.coredata.Event
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("dance_class/")
    fun getDanceClasses(): Call<List<DanceClass>>

    @GET("event/")
    fun getEvents(): Call<List<Event>>

    @GET("data/user/")
    fun getDancerProfiles(): Call<List<DancerProfile>>

//    @GET("data/user/{pk}/")
//    fun getDancerProfileById(@Path("pk") pk: Int): Call<DancerProfile>

    @POST("token/")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}