package com.mirkamalg.domain.repositories

import com.mirkamalg.domain.models.PriceEntity

/**
 * Created by Mirkamal on 16 November 2022
 */

interface PricesRepository {

    suspend fun getPrices(ids: String, currency: String): List<PriceEntity>

}