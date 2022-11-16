package com.mirkamalg.data.di

import com.mirkamalg.data.dataSource.local.CryptoHistory
import com.mirkamalg.data.dataSource.local.CryptoSettings
import com.mirkamalg.data.dataSource.remote.ApiServices
import com.mirkamalg.data.repositories.PricesRepositoryImpl
import com.mirkamalg.data.repositories.RecordsRepositoryImpl
import com.mirkamalg.data.repositories.SettingsRepositoryImpl
import com.mirkamalg.domain.repositories.PricesRepository
import com.mirkamalg.domain.repositories.RecordsRepository
import com.mirkamalg.domain.repositories.SettingsRepository
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
    fun providePricesRepository(apiServices: ApiServices): PricesRepository =
        PricesRepositoryImpl(apiServices)

    @Provides
    @Singleton
    fun provideSettingsRepository(settings: CryptoSettings): SettingsRepository =
        SettingsRepositoryImpl(settings)

    @Provides
    @Singleton
    fun provideRecordsRepository(history: CryptoHistory): RecordsRepository =
        RecordsRepositoryImpl(history)

}