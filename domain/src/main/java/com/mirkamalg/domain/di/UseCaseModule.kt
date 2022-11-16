package com.mirkamalg.domain.di

import com.mirkamalg.domain.repositories.PricesRepository
import com.mirkamalg.domain.repositories.SettingsRepository
import com.mirkamalg.domain.usecase.GetPricesUseCase
import com.mirkamalg.domain.usecase.ReadSettingForCryptoUseCase
import com.mirkamalg.domain.usecase.SaveCryptoSettingsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * Created by Mirkamal on 16 November 2022
 */

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetPricesUseCase(repository: PricesRepository) =
        GetPricesUseCase(Dispatchers.IO, repository)

    @Provides
    @Singleton
    fun provideReadSettingsForCryptoUseCase(repository: SettingsRepository) =
        ReadSettingForCryptoUseCase(Dispatchers.IO, repository)

    @Provides
    @Singleton
    fun provideSaveCryptoSettingsUseCase(repository: SettingsRepository) =
        SaveCryptoSettingsUseCase(Dispatchers.IO, repository)

}