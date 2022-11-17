package com.mirkamalg.data.repositories

import com.mirkamalg.data.dataSource.local.CryptoHistory
import com.mirkamalg.domain.repositories.RecordsRepository
import javax.inject.Inject

/**
 * Created by Mirkamal on 17 Noyabr 2022
 */

class RecordsRepositoryImpl @Inject constructor(private val cryptoHistory: CryptoHistory) :
    RecordsRepository {

    override fun saveNewRecord(`for`: String, value: Double) {
        cryptoHistory.addRecord(`for`, value)
    }

    override fun readRecords(`for`: String) = cryptoHistory.readRecords(`for`)
}