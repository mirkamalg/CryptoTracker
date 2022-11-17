package com.mirkamalg.data

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.content.Context
import android.content.SharedPreferences
import com.mirkamalg.data.dataSource.local.CryptoSettings
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Mirkamal on 17 Noyabr 2022
 */

@RunWith(MockitoJUnitRunner::class)
class CryptoSettingsTests {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    lateinit var sharedPreferences: SharedPreferences
    lateinit var context: Context
    lateinit var cryptoSettings: CryptoSettings

    @Before
    fun before() {
        context = Mockito.mock(Context::class.java)
        sharedPreferences = Mockito.mock(SharedPreferences::class.java)
        Mockito.`when`(context.getSharedPreferences(anyString(), anyInt()))
            .thenReturn(sharedPreferences)
        Mockito.`when`(sharedPreferences.getString(anyString(), anyString()))
            .thenReturn("15.0=25.0")

        cryptoSettings = CryptoSettings(context)
    }

    @Test
    fun testCryptoSettings_readMinMaxPair() {
        val pair =
            cryptoSettings.stringToMinMaxPair(sharedPreferences.getString(anyString(), anyString()))
        Assert.assertEquals(Pair(15.0, 25.0), pair)
    }

}