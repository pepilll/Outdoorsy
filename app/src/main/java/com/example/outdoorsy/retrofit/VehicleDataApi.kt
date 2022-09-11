package com.example.outdoorsy.retrofit

import com.example.outdoorsy.model.VehicleData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface VehicleDataApi {

    @GET("rentals")
    suspend fun getVehicle(
        @Query("filter[keywords]") filter: String,
        @Query("page[limit]") limit: Int,
        @Query("page[offset]") offset: Int,
        @Query("address") address: String
    ): Response<VehicleData>
}

