package com.example.dancebuddy.coredata.viewModels

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dancebuddy.api.RetrofitClient
import com.example.dancebuddy.coredata.DanceClass
import com.example.dancebuddy.coredata.user.MainUser
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class DanceClassViewModel : ViewModel() {
    private val apiService = RetrofitClient.apiService

    private val _data = MutableLiveData<List<DanceClass>>()
    val data: LiveData<List<DanceClass>> = _data

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = apiService.getDanceClasses().awaitResponse()

                if (response.isSuccessful) {
                    _data.value = response.body() ?: emptyList()
                } else {
                    _data.value = emptyList()
                }
            } catch (e: Exception) {
                Log.e("DanceClassViewModel", "Exception occurred: ${e.message}")
                e.printStackTrace()
                _data.value = emptyList()
            }
            _loading.value = false
        }
    }

    @Composable
    fun getUsersClasses(id: Int): List<DanceClass> {
        val classes by this.data.observeAsState(initial = emptyList())

        return classes.filter { it.users.contains(MainUser.data) }
    }
}
