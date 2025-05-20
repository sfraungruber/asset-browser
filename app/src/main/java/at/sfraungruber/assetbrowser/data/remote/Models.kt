@file:UseSerializers(BigDecimalSerializer::class)

package at.sfraungruber.assetbrowser.data.remote

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.math.BigDecimal

@Serializable
data class Asset(
    val id: String,
    val name: String,
    val symbol: String,
    val priceUsd: BigDecimal,
    val changePercent24Hr: BigDecimal,
)

@Serializable
data class AssetResponse(
    val data: List<Asset>,
)

@Serializable
data class Currency(
    val id: String,
    val symbol: String,
    val currencySymbol: String,
    val type: String,
    val rateUsd: BigDecimal,
)

@Serializable
data class CurrencyResponse(
    val data: Currency,
)
