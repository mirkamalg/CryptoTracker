package com.mirkamalg.data.repositories

import com.mirkamalg.data.dataSource.remote.ApiServices
import com.mirkamalg.domain.repositories.PricesRepository
import javax.inject.Inject

/**
 * Created by Mirkamal on 16 November 2022
 */

class PricesRepositoryImpl @Inject constructor(private val apiServices: ApiServices) :
    PricesRepository {

    override suspend fun getPrices(ids: String, currency: String) =
        apiServices.getPrice(ids, currency).entities
}