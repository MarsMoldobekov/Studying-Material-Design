package com.example.studyingmaterialdesign.viewmodel

import com.example.studyingmaterialdesign.domain.data.FLRResponse

sealed class FLRData {
    data class Success(val listOfFLRResponse: List<FLRResponse>) : FLRData()
    data class Error(val throwable: Throwable) : FLRData()
    object Loading : FLRData()
}
