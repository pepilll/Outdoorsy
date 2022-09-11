package com.example.outdoorsy.repository

import com.example.outdoorsy.model.VehicleData
import com.example.outdoorsy.retrofit.VehicleDataApi
import com.example.outdoorsy.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VehicleDataRepository @Inject constructor(
    private val api: VehicleDataApi
) {
    private lateinit var vehicleData: VehicleData

    suspend fun getVehicle(filter: String, pageSize:Int, startIndex:Int): Flow<DataState<VehicleData>> = flow {
        emit(DataState.Loading)
        try {
            val vehicles = api.getVehicle(filter, pageSize, startIndex, "UnitedStates")
            vehicles.body()?.let {
                vehicleData = it
            }
            emit(DataState.Success(vehicleData))
        } catch (exception: Exception) {
            emit(DataState.Error(exception))
        }
    }
}