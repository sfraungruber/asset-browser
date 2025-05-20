package at.sfraungruber.assetbrowser.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import at.sfraungruber.assetbrowser.data.remote.Asset
import at.sfraungruber.assetbrowser.data.remote.AssetResponse
import at.sfraungruber.assetbrowser.data.remote.CryptoApi
import at.sfraungruber.assetbrowser.data.remote.Currency
import at.sfraungruber.assetbrowser.data.remote.CurrencyApi
import at.sfraungruber.assetbrowser.data.remote.CurrencyResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.delay
import java.math.BigDecimal
import kotlin.time.Duration.Companion.milliseconds

private val assets = listOf(
    Asset(
        id = "1",
        symbol = "BTC",
        name = "Bitcoin",
        priceUsd = BigDecimal.valueOf(50000.00),
        changePercent24Hr = BigDecimal.valueOf(1.50)
    ),
    Asset(
        id = "2",
        symbol = "ETH",
        name = "Ethereum",
        priceUsd = BigDecimal.valueOf(3000.50),
        changePercent24Hr = BigDecimal.valueOf(-0.80)
    ),
    Asset(
        id = "3",
        symbol = "XRP",
        name = "Ripple",
        priceUsd = BigDecimal.valueOf(0.55),
        changePercent24Hr = BigDecimal.valueOf(2.10)
    ),
    Asset(
        id = "4",
        symbol = "LTC",
        name = "Litecoin",
        priceUsd = BigDecimal.valueOf(150.20),
        changePercent24Hr = BigDecimal.valueOf(0.30)
    ),
    Asset(
        id = "5",
        symbol = "ADA",
        name = "Cardano",
        priceUsd = BigDecimal.valueOf(0.40),
        changePercent24Hr = BigDecimal.valueOf(-1.20)
    ),
    Asset(
        id = "6",
        symbol = "DOGE",
        name = "Dogecoin",
        priceUsd = BigDecimal.valueOf(0.15),
        changePercent24Hr = BigDecimal.valueOf(3.50)
    ),
    Asset(
        id = "7",
        symbol = "SOL",
        name = "Solana",
        priceUsd = BigDecimal.valueOf(180.75),
        changePercent24Hr = BigDecimal.valueOf(1.80)
    ),
    Asset(
        id = "8",
        symbol = "DOT",
        name = "Polkadot",
        priceUsd = BigDecimal.valueOf(25.90),
        changePercent24Hr = BigDecimal.valueOf(-0.50)
    ),
    Asset(
        id = "9",
        symbol = "MATIC",
        name = "Polygon",
        priceUsd = BigDecimal.valueOf(1.10),
        changePercent24Hr = BigDecimal.valueOf(2.70)
    ),
    Asset(
        id = "10",
        symbol = "SHIB",
        name = "Shiba Inu",
        priceUsd = BigDecimal.valueOf(0.000025),
        changePercent24Hr = BigDecimal.valueOf(-1.00)
    ),
    Asset(
        id = "11",
        symbol = "AVAX",
        name = "Avalanche",
        priceUsd = BigDecimal.valueOf(85.30),
        changePercent24Hr = BigDecimal.valueOf(0.90)
    ),
    Asset(
        id = "12",
        symbol = "TRX",
        name = "Tron",
        priceUsd = BigDecimal.valueOf(0.08),
        changePercent24Hr = BigDecimal.valueOf(1.60)
    ),
    Asset(
        id = "13",
        symbol = "LINK",
        name = "Chainlink",
        priceUsd = BigDecimal.valueOf(18.50),
        changePercent24Hr = BigDecimal.valueOf(-0.20)
    ),
    Asset(
        id = "14",
        symbol = "XLM",
        name = "Stellar",
        priceUsd = BigDecimal.valueOf(0.12),
        changePercent24Hr = BigDecimal.valueOf(2.30)
    ),
    Asset(
        id = "15",
        symbol = "EOS",
        name = "EOS",
        priceUsd = BigDecimal.valueOf(2.80),
        changePercent24Hr = BigDecimal.valueOf(-1.50)
    ),
    Asset(
        id = "16",
        symbol = "XMR",
        name = "Monero",
        priceUsd = BigDecimal.valueOf(220.10),
        changePercent24Hr = BigDecimal.valueOf(0.60)
    ),
    Asset(
        id = "17",
        symbol = "ETC",
        name = "Ethereum Classic",
        priceUsd = BigDecimal.valueOf(35.40),
        changePercent24Hr = BigDecimal.valueOf(1.10)
    ),
    Asset(
        id = "18",
        symbol = "BNB",
        name = "Binance Coin",
        priceUsd = BigDecimal.valueOf(350.80),
        changePercent24Hr = BigDecimal.valueOf(-0.70)
    ),
    Asset(
        id = "19",
        symbol = "XTZ",
        name = "Tezos",
        priceUsd = BigDecimal.valueOf(3.20),
        changePercent24Hr = BigDecimal.valueOf(1.90)
    ),
    Asset(
        id = "20",
        symbol = "IOTA",
        name = "IOTA",
        priceUsd = BigDecimal.valueOf(0.35),
        changePercent24Hr = BigDecimal.valueOf(-1.30)
    ),
    Asset(
        id = "21",
        symbol = "ATOM",
        name = "Cosmos",
        priceUsd = BigDecimal.valueOf(30.60),
        changePercent24Hr = BigDecimal.valueOf(0.40)
    ),
    Asset(
        id = "22",
        symbol = "VET",
        name = "VeChain",
        priceUsd = BigDecimal.valueOf(0.03),
        changePercent24Hr = BigDecimal.valueOf(2.50)
    ),
    Asset(
        id = "23",
        symbol = "FIL",
        name = "Filecoin",
        priceUsd = BigDecimal.valueOf(65.20),
        changePercent24Hr = BigDecimal.valueOf(-0.90)
    ),
    Asset(
        id = "24",
        symbol = "THETA",
        name = "Theta Network",
        priceUsd = BigDecimal.valueOf(4.10),
        changePercent24Hr = BigDecimal.valueOf(1.70)
    ),
    Asset(
        id = "25",
        symbol = "UNI",
        name = "Uniswap",
        priceUsd = BigDecimal.valueOf(22.70),
        changePercent24Hr = BigDecimal.valueOf(-1.10)
    ),
    Asset(
        id = "26",
        symbol = "AAVE",
        name = "Aave",
        priceUsd = BigDecimal.valueOf(280.50),
        changePercent24Hr = BigDecimal.valueOf(0.50)
    ),
    Asset(
        id = "27",
        symbol = "MKR",
        name = "Maker",
        priceUsd = BigDecimal.valueOf(2500.90),
        changePercent24Hr = BigDecimal.valueOf(1.20)
    ),
    Asset(
        id = "28",
        symbol = "NEO",
        name = "NEO",
        priceUsd = BigDecimal.valueOf(45.30),
        changePercent24Hr = BigDecimal.valueOf(-0.60)
    ),
    Asset(
        id = "29",
        symbol = "DASH",
        name = "Dash",
        priceUsd = BigDecimal.valueOf(180.20),
        changePercent24Hr = BigDecimal.valueOf(2.00)
    ),
    Asset(
        id = "30",
        symbol = "ZEC",
        name = "Zcash",
        priceUsd = BigDecimal.valueOf(155.70),
        changePercent24Hr = BigDecimal.valueOf(-1.40)
    ),
    Asset(
        id = "31",
        symbol = "OMG",
        name = "OMG Network",
        priceUsd = BigDecimal.valueOf(6.80),
        changePercent24Hr = BigDecimal.valueOf(0.70)
    ),
    Asset(
        id = "32",
        symbol = "CHZ",
        name = "Chiliz",
        priceUsd = BigDecimal.valueOf(0.32),
        changePercent24Hr = BigDecimal.valueOf(1.80)
    ),
    Asset(
        id = "33",
        symbol = "ENJ",
        name = "Enjin Coin",
        priceUsd = BigDecimal.valueOf(1.50),
        changePercent24Hr = BigDecimal.valueOf(-0.30)
    ),
    Asset(
        id = "34",
        symbol = "BAT",
        name = "Basic Attention Token",
        priceUsd = BigDecimal.valueOf(0.75),
        changePercent24Hr = BigDecimal.valueOf(2.40)
    ),
    Asset(
        id = "35",
        symbol = "ZIL",
        name = "Zilliqa",
        priceUsd = BigDecimal.valueOf(0.10),
        changePercent24Hr = BigDecimal.valueOf(-1.60)
    ),
    Asset(
        id = "36",
        symbol = "IOST",
        name = "IOST",
        priceUsd = BigDecimal.valueOf(0.04),
        changePercent24Hr = BigDecimal.valueOf(0.80)
    ),
    Asset(
        id = "37",
        symbol = "NANO",
        name = "Nano",
        priceUsd = BigDecimal.valueOf(5.10),
        changePercent24Hr = BigDecimal.valueOf(1.30)
    ),
    Asset(
        id = "38",
        symbol = "ONT",
        name = "Ontology",
        priceUsd = BigDecimal.valueOf(1.20),
        changePercent24Hr = BigDecimal.valueOf(-0.40)
    ),
    Asset(
        id = "39",
        symbol = "QTUM",
        name = "Qtum",
        priceUsd = BigDecimal.valueOf(4.70),
        changePercent24Hr = BigDecimal.valueOf(2.10)
    ),
    Asset(
        id = "40",
        symbol = "BTG",
        name = "Bitcoin Gold",
        priceUsd = BigDecimal.valueOf(65.90),
        changePercent24Hr = BigDecimal.valueOf(-1.70)
    ),
    Asset(
        id = "41",
        symbol = "RVN",
        name = "Ravencoin",
        priceUsd = BigDecimal.valueOf(0.06),
        changePercent24Hr = BigDecimal.valueOf(0.90)
    ),
    Asset(
        id = "42",
        symbol = "ICX",
        name = "ICON",
        priceUsd = BigDecimal.valueOf(0.85),
        changePercent24Hr = BigDecimal.valueOf(1.50)
    ),
    Asset(
        id = "43",
        symbol = "WAVES",
        name = "Waves",
        priceUsd = BigDecimal.valueOf(12.30),
        changePercent24Hr = BigDecimal.valueOf(-0.20)
    ),
    Asset(
        id = "44",
        symbol = "SNX",
        name = "Synthetix",
        priceUsd = BigDecimal.valueOf(7.10),
        changePercent24Hr = BigDecimal.valueOf(2.30)
    ),
    Asset(
        id = "45",
        symbol = "YFI",
        name = "yearn.finance",
        priceUsd = BigDecimal.valueOf(35000.50),
        changePercent24Hr = BigDecimal.valueOf(-1.00)
    ),
    Asset(
        id = "46",
        symbol = "COMP",
        name = "Compound",
        priceUsd = BigDecimal.valueOf(185.20),
        changePercent24Hr = BigDecimal.valueOf(0.60)
    ),
    Asset(
        id = "47",
        symbol = "UMA",
        name = "Universal Market Access",
        priceUsd = BigDecimal.valueOf(9.40),
        changePercent24Hr = BigDecimal.valueOf(1.10)
    ),
    Asset(
        id = "48",
        symbol = "BAL",
        name = "Balancer",
        priceUsd = BigDecimal.valueOf(15.70),
        changePercent24Hr = BigDecimal.valueOf(-0.70)
    ),
    Asset(
        id = "49",
        symbol = "CRV",
        name = "Curve DAO Token",
        priceUsd = BigDecimal.valueOf(4.20),
        changePercent24Hr = BigDecimal.valueOf(1.90)
    ),
    Asset(
        id = "50",
        symbol = "SUSHI",
        name = "SushiSwap",
        priceUsd = BigDecimal.valueOf(6.30),
        changePercent24Hr = BigDecimal.valueOf(-1.30)
    ),
    Asset(
        id = "51",
        symbol = "KSM",
        name = "Kusama",
        priceUsd = BigDecimal.valueOf(155.80),
        changePercent24Hr = BigDecimal.valueOf(0.40)
    ),
    Asset(
        id = "52",
        symbol = "NEAR",
        name = "Near Protocol",
        priceUsd = BigDecimal.valueOf(16.50),
        changePercent24Hr = BigDecimal.valueOf(2.50)
    ),
    Asset(
        id = "53",
        symbol = "EGLD",
        name = "Elrond",
        priceUsd = BigDecimal.valueOf(210.30),
        changePercent24Hr = BigDecimal.valueOf(-0.90)
    ),
    Asset(
        id = "54",
        symbol = "FTM",
        name = "Fantom",
        priceUsd = BigDecimal.valueOf(0.88),
        changePercent24Hr = BigDecimal.valueOf(1.70)
    ),
    Asset(
        id = "55",
        symbol = "HNT",
        name = "Helium",
        priceUsd = BigDecimal.valueOf(22.90),
        changePercent24Hr = BigDecimal.valueOf(-1.10)
    ),
    Asset(
        id = "56",
        symbol = "FLOW",
        name = "Flow",
        priceUsd = BigDecimal.valueOf(35.60),
        changePercent24Hr = BigDecimal.valueOf(0.50)
    ),
    Asset(
        id = "57",
        symbol = "ICP",
        name = "Internet Computer",
        priceUsd = BigDecimal.valueOf(28.10),
        changePercent24Hr = BigDecimal.valueOf(1.20)
    ),
    Asset(
        id = "58",
        symbol = "THOR",
        name = "THORChain",
        priceUsd = BigDecimal.valueOf(11.40),
        changePercent24Hr = BigDecimal.valueOf(-0.60)
    ),
    Asset(
        id = "59",
        symbol = "LUNA",
        name = "Terra",
        priceUsd = BigDecimal.valueOf(85.70),
        changePercent24Hr = BigDecimal.valueOf(2.00)
    ),
    Asset(
        id = "60",
        symbol = "AXS",
        name = "Axie Infinity",
        priceUsd = BigDecimal.valueOf(60.20),
        changePercent24Hr = BigDecimal.valueOf(-1.40)
    ),
    Asset(
        id = "61",
        symbol = "SAND",
        name = "The Sandbox",
        priceUsd = BigDecimal.valueOf(3.10),
        changePercent24Hr = BigDecimal.valueOf(0.70)
    ),
    Asset(
        id = "62",
        symbol = "MANA",
        name = "Decentraland",
        priceUsd = BigDecimal.valueOf(2.80),
        changePercent24Hr = BigDecimal.valueOf(1.80)
    ),
    Asset(
        id = "63",
        symbol = "ENJ",
        name = "Efinity",
        priceUsd = BigDecimal.valueOf(2.10),
        changePercent24Hr = BigDecimal.valueOf(-0.30)
    ),
    Asset(
        id = "64",
        symbol = "CHSB",
        name = "SwissBorg",
        priceUsd = BigDecimal.valueOf(0.95),
        changePercent24Hr = BigDecimal.valueOf(2.40)
    ),
    Asset(
        id = "65",
        symbol = "RSR",
        name = "Reserve Rights",
        priceUsd = BigDecimal.valueOf(0.02),
        changePercent24Hr = BigDecimal.valueOf(-1.60)
    ),
    Asset(
        id = "66",
        symbol = "CELO",
        name = "Celo",
        priceUsd = BigDecimal.valueOf(4.30),
        changePercent24Hr = BigDecimal.valueOf(0.80)
    ),
    Asset(
        id = "67",
        symbol = "MKR",
        name = "Oasis Network",
        priceUsd = BigDecimal.valueOf(0.45),
        changePercent24Hr = BigDecimal.valueOf(1.30)
    ),
    Asset(
        id = "68",
        symbol = "GRT",
        name = "The Graph",
        priceUsd = BigDecimal.valueOf(0.60),
        changePercent24Hr = BigDecimal.valueOf(-0.40)
    ),
    Asset(
        id = "69",
        symbol = "SNX",
        name = "Serum",
        priceUsd = BigDecimal.valueOf(3.70),
        changePercent24Hr = BigDecimal.valueOf(2.10)
    ),
    Asset(
        id = "70",
        symbol = "SRM",
        name = "Sirin Labs Token",
        priceUsd = BigDecimal.valueOf(0.18),
        changePercent24Hr = BigDecimal.valueOf(-1.70)
    ),
    Asset(
        id = "71",
        symbol = "GALA",
        name = "Gala",
        priceUsd = BigDecimal.valueOf(0.25),
        changePercent24Hr = BigDecimal.valueOf(0.90)
    ),
    Asset(
        id = "72",
        symbol = "ONE",
        name = "Harmony",
        priceUsd = BigDecimal.valueOf(0.11),
        changePercent24Hr = BigDecimal.valueOf(1.50)
    ),
    Asset(
        id = "73",
        symbol = "IOTA",
        name = "IOTA",
        priceUsd = BigDecimal.valueOf(0.42),
        changePercent24Hr = BigDecimal.valueOf(-0.20)
    ),
    Asset(
        id = "74",
        symbol = "DCR",
        name = "Decred",
        priceUsd = BigDecimal.valueOf(75.50),
        changePercent24Hr = BigDecimal.valueOf(2.30)
    ),
    Asset(
        id = "75",
        symbol = "KNC",
        name = "Kyber Network Crystal",
        priceUsd = BigDecimal.valueOf(2.90),
        changePercent24Hr = BigDecimal.valueOf(-1.00)
    ),
    Asset(
        id = "76",
        symbol = "ZRX",
        name = "0x",
        priceUsd = BigDecimal.valueOf(0.80),
        changePercent24Hr = BigDecimal.valueOf(0.60)
    ),
    Asset(
        id = "77",
        symbol = "BAND",
        name = "Band Protocol",
        priceUsd = BigDecimal.valueOf(5.20),
        changePercent24Hr = BigDecimal.valueOf(1.10)
    ),
    Asset(
        id = "78",
        symbol = "ANKR",
        name = "Ankr",
        priceUsd = BigDecimal.valueOf(0.07),
        changePercent24Hr = BigDecimal.valueOf(-0.70)
    ),
    Asset(
        id = "79",
        symbol = "BNT",
        name = "Bancor",
        priceUsd = BigDecimal.valueOf(3.50),
        changePercent24Hr = BigDecimal.valueOf(1.90)
    ),
    Asset(
        id = "80",
        symbol = "SXP",
        name = "Swipe",
        priceUsd = BigDecimal.valueOf(2.20),
        changePercent24Hr = BigDecimal.valueOf(-1.30)
    ),
)

private val currencies = mapOf(
    "united-states-dollar" to Currency(
        id = "united-states-dollar",
        symbol = "USD",
        currencySymbol = "$",
        type = "fiat",
        rateUsd = BigDecimal.valueOf(1),
    ),
    "euro" to Currency(
        id = "euro",
        symbol = "EUR",
        currencySymbol = "€",
        type = "fiat",
        rateUsd = BigDecimal.valueOf(1.14),
    ),
    "british-pound-sterling" to Currency(
        id = "british-pound-sterling",
        symbol = "GBP",
        currencySymbol = "£",
        type = "fiat",
        rateUsd = BigDecimal.valueOf(1.33),
    )
)

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private fun isOnline(
        appContext: Context,
    ): Boolean {
        val connectivityManager = appContext.getSystemService(
            /* name = */ Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val capabilities = connectivityManager.getNetworkCapabilities(
            /* network = */ connectivityManager.activeNetwork
        )

        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return true
            }
        }
        return false
    }

    @Provides
    fun provideCryptoApi(
        @ApplicationContext appContext: Context,
    ): CryptoApi {
        return object : CryptoApi {
            override suspend fun getAsset(): AssetResponse {
                checkNetworkOrThrow(appContext)

                delay(500.milliseconds)
                return AssetResponse(
                    data = assets,
                )
            }
        }
    }

    @Provides
    fun provideCurrencyApi(
        @ApplicationContext appContext: Context,
    ): CurrencyApi {
        return object : CurrencyApi {
            override suspend fun getRateById(id: String): CurrencyResponse {
                checkNetworkOrThrow(appContext)

                delay(500.milliseconds)
                return CurrencyResponse(
                    data = currencies[id]!!,
                )
            }

        }
    }

    private fun checkNetworkOrThrow(appContext: Context) {
        if (!isOnline(appContext)) error("no network")
    }
}
