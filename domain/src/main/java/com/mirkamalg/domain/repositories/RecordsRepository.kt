package com.mirkamalg.domain.repositories

/**
 * Created by Mirkamal on 17 Noyabr 2022
 */

interface RecordsRepository {

    fun saveNewRecord(`for`: String, value: Double)

}