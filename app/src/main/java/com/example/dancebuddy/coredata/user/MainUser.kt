package com.example.dancebuddy.coredata.user

import android.util.Log
import com.example.dancebuddy.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Instant

object MainUser : UserProfile {
    lateinit var data: DancerProfile

    fun fetchUserData(id: Int) {
        val call = RetrofitClient.apiService.getUserById(id)

        call.enqueue(object : Callback<DancerProfile> {
            override fun onResponse(call: Call<DancerProfile>, response: Response<DancerProfile>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        data = it
                    }
                    Log.i("UserData", "User Data: $data")
                } else {
                    Log.i("UserData", "User Data Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<DancerProfile>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    override val id: Int
        get() = data.id
    override val email: String
        get() = data.email
    override val password: String
        get() = data.password
    override val image: String?
        get() = data.image
    override val dateJoined: Instant
        get() = data.dateJoined
    override val firstName: String
        get() = data.firstName
    override val lastName: String
        get() = data.lastName
    override val username: String
        get() = data.username
    override val phoneNumber: String?
        get() = data.phoneNumber
}