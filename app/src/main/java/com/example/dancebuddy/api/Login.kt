package com.example.dancebuddy.api

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val access: String, val refresh: String)