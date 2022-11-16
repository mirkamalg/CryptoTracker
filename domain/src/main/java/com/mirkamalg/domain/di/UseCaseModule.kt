package com.mirkamalg.domain.di

import com.mirkamalg.domain.repositories.PricesRepository
import com.mirkamalg.domain.usecase.GetPricesUseCase
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
    fun provideGetPricesUseCase(repository: PricesRepository) = GetPricesUseCase(Dispatchers.IO, repository)

}