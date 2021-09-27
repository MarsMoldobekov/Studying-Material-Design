package com.example.studyingmaterialdesign.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.example.studyingmaterialdesign.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class ViewModel : ViewModel() {
    private val liveDataPOD = MutableLiveData<PODData>()
    private val liveDataFLR = MutableLiveData<FLRData>()
    private val liveDataEPIC = MutableLiveData<EPICData>()
    private val repository = Repository()

    fun loadPOD(date: String) {
        liveDataPOD.value = PODData.Loading
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = repository.loadPOD(date)
                    if (response.isSuccessful) {
                        liveDataPOD.postValue(response.body()?.let { PODData.Success(it) })
                    } else {
                        //TODO(make error body user-friendly)
                        liveDataPOD.postValue(PODData.Error(Throwable(response.errorBody().toString())))
                    }
                } catch (exception: Exception) {
                    liveDataPOD.postValue(PODData.Error(exception))
                }
            }
        }
    }

    fun loadFLR(startDate: String, endDate: String) {
        liveDataFLR.value = FLRData.Loading
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = repository.loadFLR(startDate, endDate)
                    if (response.isSuccessful) {
                        liveDataFLR.postValue(response.body()?.let { FLRData.Success(it) })
                    } else {
                        //TODO(make error body user-friendly)
                        liveDataFLR.postValue(FLRData.Error(Throwable(response.errorBody().toString())))
                    }
                } catch (exception: Exception) {
                    liveDataFLR.postValue(FLRData.Error(exception))
                }
            }
        }
    }

    fun loadEPIC() {
        liveDataEPIC.value = EPICData.Loading
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = repository.loadEPIC()
                    if (response.isSuccessful) {
                        liveDataEPIC.postValue(response.body()?.let { EPICData.Success(it) })
                    } else {
                        //TODO(make error body user-friendly)
                        liveDataEPIC.postValue(EPICData.Error(Throwable(response.errorBody().toString())))
                    }
                } catch (exception: Exception) {
                    liveDataEPIC.postValue(EPICData.Error(exception))
                }
            }
        }
    }

    fun getLiveDataPOD(): LiveData<PODData> = liveDataPOD

    fun getLiveDataFLR(): LiveData<FLRData> = liveDataFLR

    fun getLiveDataEPIC(): LiveData<EPICData> = liveDataEPIC
}