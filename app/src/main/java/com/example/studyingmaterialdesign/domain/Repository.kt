package com.example.studyingmaterialdesign.domain

import com.example.studyingmaterialdesign.BuildConfig
import com.example.studyingmaterialdesign.domain.api.PODApi
import com.example.studyingmaterialdesign.domain.data.PODResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class Repository {
    private val podApi: PODApi = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(
            OkHttpClient.Builder().apply {
                addInterceptor(PODInterceptor())
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            }.build()
        )
        .build()
        .create(PODApi::class.java)

    suspend fun load(date: String): retrofit2.Response<PODResponse> =
        podApi.getPictureOfTheDay(date, BuildConfig.NASA_API_KEY)

    private class PODInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(chain.request())
    }
}