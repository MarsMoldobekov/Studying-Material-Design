package com.example.studyingmaterialdesign.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.example.studyingmaterialdesign.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class ViewModel : ViewModel() {
    private val liveData = MutableLiveData<PODData>()
    private val repository = Repository()

    fun load(date: String) {
        liveData.value = PODData.Loading
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = repository.load(date)
                    if (response.isSuccessful) {
                        liveData.postValue(response.body()?.let { PODData.Success(it) })
                    } else {
                        //TODO(make error body user-friendly)
                        liveData.postValue(PODData.Error(Throwable(response.errorBody().toString())))
                    }
                } catch (exception: Exception) {
                    liveData.postValue(PODData.Error(exception))
                }
            }
        }
    }

    fun getLiveDataPOD(): LiveData<PODData> = liveData
}