package com.mirkamalg.domain.repositories

/**
 * Created by Mirkamal on 16 Noyabr 2022
 */

interface SettingsRepository {

    fun loadSettings(`for`: String): Pair<Double, Double>

    fun saveSettings(`for`: String, min: Double, max: Double)
}