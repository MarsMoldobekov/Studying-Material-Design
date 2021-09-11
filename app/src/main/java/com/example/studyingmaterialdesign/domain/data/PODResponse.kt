package com.example.studyingmaterialdesign.domain.data

import com.google.gson.annotations.SerializedName

data class PODResponse(
    @field:SerializedName(value = "copyright") val copyright: String?,
    @field:SerializedName(value = "date") val date: String?,
    @field:SerializedName(value = "explanation") val explanation: String?,
    @field:SerializedName(value = "media_type") val mediaType: String?,
    @field:SerializedName(value = "title") val title: String?,
    @field:SerializedName(value = "url") val url: String?,
    @field:SerializedName(value = "hdurl") val hdurl: String?,
)
