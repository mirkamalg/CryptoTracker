package com.mirkamalg.domain.usecase

import com.mirkamalg.domain.models.PriceEntity
import com.mirkamalg.domain.repositories.PricesRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by Mirkamal on 16 November 2022
 */

class GetPricesUseCase @Inject constructor(
    context: CoroutineContext,
    private val repository: PricesRepository
) : BaseUseCase<String, PriceEntity>(context) {

    override suspend fun onExecute(input: String) = repository.getPrices(input)

}