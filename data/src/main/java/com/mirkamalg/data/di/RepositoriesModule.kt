package com.mirkamalg.data.di

import com.mirkamalg.data.dataSource.remote.ApiServices
import com.mirkamalg.data.repositories.PricesRepositoryImpl
import com.mirkamalg.domain.repositories.PricesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Mirkamal on 16 November 2022
 */

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    @Singleton
    fun providePricesRepository(apiServices: ApiServices): PricesRepository = PricesRepositoryImpl(apiServices)

}