package com.example.studyingmaterialdesign.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.studyingmaterialdesign.domain.Repository

class ViewModel : ViewModel() {
    private val repository = Repository()

    //TODO(learn liveData, coroutine scope, and coroutines)
    fun getLiveDataPOD(): LiveData<PODData> = liveData {
        try {
            emit(PODData.Loading)
            val response = repository.load()
            if (response.isSuccessful) {
                response.body()?.let { emit(PODData.Success(it)) }
            } else {
                //TODO(catch errors)
            }
        } catch (exception: Exception) {
            emit(PODData.Error(exception))
        }
    }
}