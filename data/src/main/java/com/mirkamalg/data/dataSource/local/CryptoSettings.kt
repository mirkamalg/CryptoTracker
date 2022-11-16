package com.mirkamalg.data.dataSource.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Mirkamal on 16 Noyabr 2022
 */

@Singleton
class CryptoSettings @Inject constructor(@ApplicationContext context: Context) {

    private val preferences: SharedPreferences by lazy {
        context.getSharedPreferences("crypto_settings", Context.MODE_PRIVATE)
    }

    fun readMinMaxPair(`for`: String) = preferences.getString(`for`, null)?.let {
        it.split("=").run { Pair(get(0).toDoubleOrNull() ?: 0.0, get(1).toDoubleOrNull() ?: 0.0) }
    } ?: Pair(0.0, 0.0)

    fun updateMinMaxPair(`for`: String, min: Double, max: Double) = preferences.edit {
        putString(`for`, "$min=$max")  //Save as serialized mapping
    }

}