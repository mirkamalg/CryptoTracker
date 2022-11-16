package com.mirkamalg.data.dataSource.remote.models

import com.mirkamalg.domain.models.PriceEntity
import com.squareup.moshi.JsonClass

/**
 * Created by Mirkamal on 16 November 2022
 */

@JsonClass(generateAdapter = true)
data class PricesDto(
    val bitcoin: Price?,
    val ethereum: Price?,
    val ripple: Price?,
) {
    val entities: List<PriceEntity>
        get() = listOf(
            PriceEntity("Bitcoin", "bitcoind", bitcoin?.usd),
            PriceEntity("Ethereum", "ethereum", ethereum?.usd),
            PriceEntity("Ripple", "ripple", ripple?.usd),
        )
}

@JsonClass(generateAdapter = true)
data class Price(val usd: Double? = null)