package com.example.studyingmaterialdesign.viewmodel

import com.example.studyingmaterialdesign.domain.data.PODResponse

sealed class PODData {
    data class Success(val podResponse: PODResponse) : PODData()
    data class Error(val throwable: Throwable) : PODData()
    object Loading : PODData()
}
