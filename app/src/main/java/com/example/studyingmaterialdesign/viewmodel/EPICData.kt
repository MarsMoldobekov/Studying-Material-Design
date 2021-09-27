package com.example.studyingmaterialdesign.viewmodel

import com.example.studyingmaterialdesign.domain.data.EarthEpicServerResponse

sealed class EPICData {
    data class Success(val listOfEarthEpicServerResponse: List<EarthEpicServerResponse>) :
        EPICData()

    data class Error(val throwable: Throwable) : EPICData()
    object Loading : EPICData()
}
