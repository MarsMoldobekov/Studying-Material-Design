package com.example.studyingmaterialdesign.domain.api

import com.example.studyingmaterialdesign.domain.data.EarthEpicServerResponse
import com.example.studyingmaterialdesign.domain.data.FLRResponse
import com.example.studyingmaterialdesign.domain.data.PODResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {
    @GET(value = "planetary/apod")
    suspend fun getPOD(
        @Query(value = "date") date: String,
        @Query(value = "api_key") apiKey: String
    ): Response<PODResponse>

    @GET(value = "DONKI/FLR")
    suspend fun getFLR(
        @Query(value = "startDate") startDate: String,
        @Query(value = "endDate") endDate: String,
        @Query(value = "api_key") apiKey: String
    ): Response<List<FLRResponse>>

    @GET(value = "EPIC/api/natural")
    suspend fun getEPIC(
        @Query(value = "api_key") apiKey: String
    ): Response<List<EarthEpicServerResponse>>
}
