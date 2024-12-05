package com.example.dancebuddy.coredata.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dancebuddy.api.RetrofitClient
import com.example.dancebuddy.coredata.user.User
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class UserViewModel : ViewModel() {
    private val apiService = RetrofitClient.apiService

    private val _data = MutableLiveData<List<User>>()
    val data: LiveData<List<User>> = _data

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = apiService.getUsers().awaitResponse()

                if (response.isSuccessful) {
                    _data.value = response.body() ?: emptyList()
                } else {
                    _data.value = emptyList()
                }
            } catch (e: Exception) {
                Log.e("DancerProfileViewModel", "Exception occurred: ${e.message}")
                e.printStackTrace()
                _data.value = emptyList()
            }
            _loading.value = false
        }
    }
}