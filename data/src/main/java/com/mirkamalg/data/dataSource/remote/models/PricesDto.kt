package com.mirkamalg.data.dataSource.remote.models

import com.mirkamalg.domain.models.PriceEntity
import com.squareup.moshi.JsonClass

/**
 * Created by Mirkamal on 16 November 2022
 */

@JsonClass(generateAdapter = true)
data class PricesDto(
    val todo: Any?
) {
    val entity: PriceEntity
        get() = PriceEntity(todo)
}