package com.example.outdoorsy.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.outdoorsy.model.VehicleData
import com.example.outdoorsy.repository.VehicleDataRepository
import com.example.outdoorsy.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val vehicleDataRepository: VehicleDataRepository
) : ViewModel() {
    private val _dataStateLiveData: MutableLiveData<DataState<VehicleData?>> =
        MutableLiveData()
    val dataStateLiveData: MutableLiveData<DataState<VehicleData?>>
        get() = _dataStateLiveData

    private val _progressLiveData: MutableLiveData<Boolean> =
        MutableLiveData()
    val progressLiveData: LiveData<Boolean>
        get() = _progressLiveData

    fun setProgressLiveData(isVisible: Boolean) {
        _progressLiveData.value = isVisible
    }

    var isLastPage: Boolean = false

    fun setStateEvent(filter: String, pageSize:Int, startIndex:Int) {
        viewModelScope.launch {
            vehicleDataRepository.getVehicle(filter, pageSize, startIndex)
                .onEach { dataState ->
                    _dataStateLiveData.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }
}