package com.example.buss

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.example.AuthRequest
import com.example.example.AuthResponse
import com.example.example.RegisterRequest
import com.example.example.RegisterResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyViewModel : ViewModel() {


    private val _cityList = MutableLiveData<List<String>>()
    val cityListLiveData: LiveData<List<String>> get() = _cityList

    init {
        viewModelScope.launch {
            getCityList()
        }

    }

    private suspend fun getCityList() {
        val response = RetrofitClient.busApiService.getAllCities()
        _cityList.value = response.data
    }

    val findTrip = MutableLiveData<FindTrip>()
    val findTripLiveData: LiveData<FindTrip> get() = findTrip

    suspend fun findTrip(departure: String, arrival: String, date: String, adult: Int) {
        viewModelScope.launch {
            val response =
                RetrofitClient.busApiService.findTrip(departure, arrival, date, adult, "TR", "TR")
            findTrip.value = response
        }
    }

    val authResponse = MutableLiveData<AuthResponse>()
    val authResponseLiveData: LiveData<AuthResponse> get() = authResponse




    fun authenticate(email: String, password: String, navController: NavController) {
        viewModelScope.launch {
            val authRequest = AuthRequest(email, password)
            RetrofitClient.busApiService.authenticate(authRequest).enqueue(
                object : Callback<AuthResponse> {
                    override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                        Log.e("AuthViewModel", "Authentication failed", t)
                    }

                    override fun onResponse(
                        call: Call<AuthResponse>,
                        response: Response<AuthResponse>
                    ) {
                        if (response.isSuccessful) {
                            authResponse.value = response.body()
                            navController.navigate("homePage")
                        } else {
                            Log.e(
                                "AuthViewModel",
                                "Authentication error: ${response.errorBody()?.string()}"
                            )
                        }
                    }


                })
        }
    }

    var _registerResponse = MutableStateFlow<RegisterResponse>(RegisterResponse(false, null, null, null))

    suspend fun registerUser(
        firstName: String,
        lastName: String,
        email: String,
        userName: String,
        password: String,
        confirmPassword: String
    ) {
        viewModelScope.launch {
            val registerRequest =
                RegisterRequest(firstName, lastName, email, userName, password, confirmPassword)
            RetrofitClient.busApiService.register(registerRequest)
                .enqueue(object : Callback<RegisterResponse> {
                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        Log.e("RegisterViewModel", "Registration failed", t)
                    }

                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {
                        if (response.isSuccessful) {
                            _registerResponse.value = response.body()!!

                            Log.d(
                                "RegisterViewModel",
                                "Registration successful: ${response.body()}"
                            )
                        } else {
                            Log.e(
                                "RegisterViewModel",
                                "Registration error: ${response.errorBody()?.string()}"
                            )
                        }
                    }
                })
        }
    }
}