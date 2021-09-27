package com.example.studyingmaterialdesign.domain.data

data class FLRResponse(
    val activeRegionNum: Int,
    val beginTime: String,
    val classType: String,
    val endTime: String?,
    val flrID: String,
    val instruments: List<Instrument>,
    val link: String,
    val linkedEvents: List<LinkedEvent>,
    val peakTime: String,
    val sourceLocation: String
)