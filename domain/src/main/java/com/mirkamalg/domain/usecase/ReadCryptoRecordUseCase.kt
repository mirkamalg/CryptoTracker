package com.mirkamalg.domain.usecase

import com.mirkamalg.domain.repositories.RecordsRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by Mirkamal on 17 Noyabr 2022
 */

class ReadCryptoRecordUseCase @Inject constructor(
    context: CoroutineContext,
    private val repository: RecordsRepository
) :
    BaseUseCase<String, List<Double>>(context) {

    override suspend fun onExecute(input: String) = repository.readRecords(input)
}