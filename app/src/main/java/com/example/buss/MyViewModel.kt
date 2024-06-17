package com.example.buss

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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

    lateinit var selectedCity: DataCities
    private val _cityList = MutableLiveData<List<String>>()
    val cityListLiveData: LiveData<List<String>> get() = _cityList

    init {
        viewModelScope.launch {
            getCityList()
            getAllCities()
            getBusBrands()
        }
    }

    var _busBrandComments = MutableStateFlow<BusReview>(BusReview(false, null, null, arrayListOf()))

    suspend fun getBusBrandComments() {
        val response = RetrofitClient.busApiService.getBusBrandComments(selectedBusBrand.id!!)
        _busBrandComments.value = response
    }


    val followedTrips = MutableLiveData<AllFollowedTrips>()

    suspend fun getFollowedTrips() {
        viewModelScope.launch {
            Log.d("MyViewModel", "getFollowedTrips")
            val response = RetrofitClient.busApiService.getAllFollowTrips()
            followedTrips.value = response

        }
    }

    lateinit var selectedBusBrand: DataBus
    private var _busBrands = MutableLiveData<AllBusBrands>()
    val busBrandsLiveData: LiveData<AllBusBrands> get() = _busBrands

    private suspend fun getBusBrands() {
        viewModelScope.launch {
            val response = RetrofitClient.busApiService.getAllBusBrands()
            _busBrands.value = response
        }
    }


    private suspend fun getCityList() {
        val response = RetrofitClient.busApiService.getAllCityNames()
        _cityList.value = response.data
    }

    var allCities = MutableLiveData<AllCities>()
    val allCitiesLiveData: LiveData<AllCities> get() = allCities

    suspend fun getAllCities() {
        viewModelScope.launch {
            val response = RetrofitClient.busApiService.getAllCities()
            allCities.value = response
        }
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

    var authResponse = MutableLiveData<AuthResponse>()
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
                            RetrofitClient.BEARER_TOKEN = response.body()?.data?.jwToken ?: ""
                            Log.d("JWToken", response.body()?.data?.jwToken ?: "")
                            authResponse.value = response.body()
                            navController.navigate("homePage")
                        } else {
                            Toast.makeText(
                                navController.context,
                                "Authentication failed",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.e(
                                "AuthViewModel",
                                "Authentication error: ${response.errorBody()?.string()}"
                            )
                        }
                    }


                })
        }
    }

    var _registerResponse =
        MutableStateFlow<RegisterResponse>(RegisterResponse(false, null, null, null))

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


    private val _followTripResponse = MutableLiveData<FollowTripResponse>()

    fun followTrip(city1: String, city2: String, date: String) {
        viewModelScope.launch {
            val authRequest = FollowTripRequest("TR", city1, "TR", city2, date)
            val response = RetrofitClient.busApiService.followTrip(authRequest).enqueue(
                object : Callback<FollowTripResponse> {
                    override fun onFailure(call: Call<FollowTripResponse>, t: Throwable) {
                        Log.e("FollowTripResponse", "FollowTripResponse", t)
                    }

                    override fun onResponse(
                        call: Call<FollowTripResponse>,
                        response: Response<FollowTripResponse>
                    ) {
                        if (response.isSuccessful) {
                            _followTripResponse.value = response.body()

                        } else {

                            Log.e(
                                "AuthViewModel",
                                "FollowTripResponse errrroooorrrrr}"
                            )
                        }
                    }


                })
        }
    }
}