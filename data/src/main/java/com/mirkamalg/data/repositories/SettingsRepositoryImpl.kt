package com.mirkamalg.data.repositories

import com.mirkamalg.data.dataSource.local.CryptoSettings
import com.mirkamalg.domain.repositories.SettingsRepository
import javax.inject.Inject

/**
 * Created by Mirkamal on 16 Noyabr 2022
 */

class SettingsRepositoryImpl @Inject constructor(private val settings: CryptoSettings) :
    SettingsRepository {

    override fun loadSettings(`for`: String): Pair<Double, Double> = settings.readMinMaxPair(`for`)

    override fun saveSettings(`for`: String, min: Double, max: Double) =
        settings.updateMinMaxPair(`for`, min, max)
}