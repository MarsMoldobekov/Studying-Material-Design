package com.example.studyingmaterialdesign.domain

import com.example.studyingmaterialdesign.BuildConfig
import com.example.studyingmaterialdesign.domain.api.NasaApi
import com.example.studyingmaterialdesign.domain.data.EarthEpicServerResponse
import com.example.studyingmaterialdesign.domain.data.FLRResponse
import com.example.studyingmaterialdesign.domain.data.PODResponse
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class Repository {
    private val nasaApi: NasaApi = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(
            OkHttpClient.Builder().apply {
                addInterceptor(PODInterceptor())
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            }.build()
        )
        .build()
        .create(NasaApi::class.java)

    suspend fun loadPOD(date: String): retrofit2.Response<PODResponse> =
        nasaApi.getPOD(date, BuildConfig.NASA_API_KEY)

    suspend fun loadFLR(startDate: String, endDate: String): retrofit2.Response<List<FLRResponse>> =
        nasaApi.getFLR(startDate, endDate, BuildConfig.NASA_API_KEY)

    suspend fun loadEPIC(): retrofit2.Response<List<EarthEpicServerResponse>> =
        nasaApi.getEPIC(BuildConfig.NASA_API_KEY)

    private class PODInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(chain.request())
    }
}