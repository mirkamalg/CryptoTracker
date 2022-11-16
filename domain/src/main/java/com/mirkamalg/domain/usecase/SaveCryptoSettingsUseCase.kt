package com.mirkamalg.domain.usecase

import com.mirkamalg.domain.repositories.SettingsRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by Mirkamal on 16 Noyabr 2022
 */

class SaveCryptoSettingsUseCase @Inject constructor(
    context: CoroutineContext,
    private val repository: SettingsRepository
) : BaseUseCase<SaveCryptoSettingsUseCase.SaveCryptoSettingsParams, Void?>(context) {

    data class SaveCryptoSettingsParams(
        val cryptoName: String,
        val min: String,
        val max: String
    )

    override suspend fun onExecute(input: SaveCryptoSettingsParams): Void? {
        repository.saveSettings(input.cryptoName, input.min.toDouble(), input.max.toDouble())
        return null
    }

}