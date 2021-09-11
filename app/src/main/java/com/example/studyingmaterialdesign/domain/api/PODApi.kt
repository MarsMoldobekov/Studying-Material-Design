package com.example.studyingmaterialdesign.domain.api

import com.example.studyingmaterialdesign.BuildConfig
import com.example.studyingmaterialdesign.domain.data.PODResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PODApi {
    @GET(value = BuildConfig.POD)
    suspend fun getPictureOfTheDay(@Query(value = "api_key") apiKey: String): Response<PODResponse>
}
