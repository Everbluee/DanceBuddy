package com.example.dancebuddy.api

import com.example.dancebuddy.coredata.DanceClass
import com.example.dancebuddy.coredata.Event
import com.example.dancebuddy.coredata.DancerProfile
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("data/dance_class/")
    fun getDanceClasses(): Call<List<DanceClass>>

    @GET("data/event/")
    fun getEvents(): Call<List<Event>>

    @GET("data/user/")
    fun getDancerProfiles(): Call<List<DancerProfile>>

    @GET("data/user/{pk}/")
    fun getDancerProfileById(@Path("pk") pk: Int): Call<DancerProfile>
}