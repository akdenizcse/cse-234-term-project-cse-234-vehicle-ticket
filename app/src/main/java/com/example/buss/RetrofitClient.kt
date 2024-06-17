package com.example.buss

import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.buscanner.tech"
    var BEARER_TOKEN = ""//"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJPcmhhbnkiLCJqdGkiOiI0Y2E4ZmI4ZC02ZTAxLTRhMzEtOGQwNC0zZjViODFiZjI1N2EiLCJlbWFpbCI6InZpbGRvbmVtQGdtYWlsLmNvbSIsInVpZCI6IjVkODcwMWMyLWY3ZDAtNDUxMC1hY2RjLWUyODk0ZTY0MjVjNSIsImlwIjoiMTI3LjAuMS4xIiwicm9sZXMiOiJCYXNpYyIsImV4cCI6MTcxNzE2NTcxNCwiaXNzIjoiQ29yZUlkZW50aXR5IiwiYXVkIjoiQ29yZUlkZW50aXR5VXNlciJ9.21GYHF1_zTeHKMdBGsqcxToIjMcrKormFgCRJxId6ZU"

    // Interceptor to add Bearer token to requests
    private val authInterceptor = Interceptor { chain ->
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("Authorization", "Bearer $BEARER_TOKEN")
        val request = requestBuilder.build()
        chain.proceed(request)
    }


    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(authInterceptor) // Add the auth interceptor
        .build()


    private val gson = GsonBuilder()
        .setLenient()
        .create()



    val busApiService: BusApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
            .create(BusApiService::class.java)
    }
}
