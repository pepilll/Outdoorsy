package com.example.outdoorsy.di

import com.example.outdoorsy.repository.VehicleDataRepository
import com.example.outdoorsy.retrofit.VehicleDataApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepositoryModule(
        api: VehicleDataApi
    ): VehicleDataRepository {
        return VehicleDataRepository(api)
    }
}
