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
) : BaseUseCase<GetPricesUseCase.GetPricesParams, List<PriceEntity>>(context) {

    data class GetPricesParams(val ids: String, val currency: String)

    override suspend fun onExecute(input: GetPricesParams) = repository.getPrices(
        input.ids, input.currency
    )

}