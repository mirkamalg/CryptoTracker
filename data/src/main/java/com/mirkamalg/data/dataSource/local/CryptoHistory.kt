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
class CryptoHistory @Inject constructor(@ApplicationContext private val context: Context) {

    private val preferences: SharedPreferences by lazy {
        context.getSharedPreferences("crypto_records", Context.MODE_PRIVATE)
    }

    fun addRecord(`for`: String, value: Double) {
        val current = preferences.getString(`for`, "")!!
        val list = current.split(",")
            .mapNotNull { it.toDoubleOrNull() }.toMutableList()
        list.add(value)
        val new = list.joinToString(separator = ",")
        preferences.edit {
            putString(`for`, new)
        }
    }

}