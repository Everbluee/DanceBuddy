package com.example.dancebuddy.coredata.user

import android.util.Log
import com.example.dancebuddy.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Instant
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object MainUser : UserProfile {
    lateinit var data: User

    suspend fun fetchUserData(id: Int): User {
        return suspendCoroutine { continuation ->
            val call = RetrofitClient.apiService.getUserById(id)

            call.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            data = it
                            Log.i("UserData", "User Data: $data")
                            continuation.resume(it)
                        }
                    } else {
                        continuation.resumeWithException(Exception("Failed to fetch user data"))
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
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