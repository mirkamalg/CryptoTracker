package com.mirkamalg.domain.usecase

import com.mirkamalg.domain.repositories.SettingsRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by Mirkamal on 16 Noyabr 2022
 */

class ReadSettingForCryptoUseCase @Inject constructor(
    context: CoroutineContext,
    private val repository: SettingsRepository
) : BaseUseCase<String, Pair<Double, Double>>(context) {

    override suspend fun onExecute(input: String) = repository.loadSettings(input)

}