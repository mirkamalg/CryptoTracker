package com.mirkamalg.data.dataSource.remote

import com.mirkamalg.data.dataSource.remote.models.PricesDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Mirkamal on 16 November 2022
 */

interface ApiServices {

    @GET("simple/price")
    suspend fun getPrice(@Query("ids") ids: String): PricesDto

}