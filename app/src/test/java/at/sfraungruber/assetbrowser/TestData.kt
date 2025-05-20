package at.sfraungruber.assetbrowser

import at.sfraungruber.assetbrowser.data.remote.Asset
import at.sfraungruber.assetbrowser.data.remote.AssetResponse
import at.sfraungruber.assetbrowser.data.remote.Currency
import at.sfraungruber.assetbrowser.data.remote.CurrencyResponse
import at.sfraungruber.assetbrowser.ui.coins.CoinUIModel
import at.sfraungruber.assetbrowser.ui.coins.CurrencyUIModel
import kotlinx.collections.immutable.persistentListOf
import java.math.BigDecimal

val currency = Currency(
    id = "euro",
    symbol = "EUR",
    currencySymbol = "€",
    type = "fiat",
    rateUsd = BigDecimal.valueOf(1.1),
)

val currencyResponse = CurrencyResponse(
    data = currency,
)

val assets = listOf(
    Asset(
        id = "1",
        symbol = "BTC",
        name = "Bitcoin",
        priceUsd = BigDecimal.valueOf(50000.0),
        changePercent24Hr = BigDecimal.valueOf(1.5),
    ),
    Asset(
        id = "2",
        symbol = "ETH",
        name = "Ethereum",
        priceUsd = BigDecimal.valueOf(3000.0),
        changePercent24Hr = BigDecimal.valueOf(-0.5),
    ),
    Asset(
        id = "3",
        symbol = "LTC",
        name = "Litecoin",
        priceUsd = BigDecimal.valueOf(150.0),
        changePercent24Hr = BigDecimal.valueOf(2.0),
    ),
    Asset(
        id = "4",
        symbol = "XRP",
        name = "Ripple",
        priceUsd = BigDecimal.valueOf(1.0),
        changePercent24Hr = BigDecimal.valueOf(-1.0),
    ),
    Asset(
        id = "5",
        symbol = "ADA",
        name = "Cardano",
        priceUsd = BigDecimal.valueOf(2.0),
        changePercent24Hr = BigDecimal.valueOf(0.0),
    ),
    Asset(
        id = "6",
        symbol = "DOT",
        name = "Polkadot",
        priceUsd = BigDecimal.valueOf(25.0),
        changePercent24Hr = BigDecimal.valueOf(3.0),
    ),
    Asset(
        id = "7",
        symbol = "SOL",
        name = "Solana",
        priceUsd = BigDecimal.valueOf(100.0),
        changePercent24Hr = BigDecimal.valueOf(-2.5),
    ),
    Asset(
        id = "8",
        symbol = "AVAX",
        name = "Avalanche",
        priceUsd = BigDecimal.valueOf(80.0),
        changePercent24Hr = BigDecimal.valueOf(1.0),
    ),
    Asset(
        id = "9",
        symbol = "DOGE",
        name = "Dogecoin",
        priceUsd = BigDecimal.valueOf(0.25),
        changePercent24Hr = BigDecimal.valueOf(0.5),
    ),
    Asset(
        id = "10",
        symbol = "SHIB",
        name = "Shiba Inu",
        priceUsd = BigDecimal.valueOf(0.00003),
        changePercent24Hr = BigDecimal.valueOf(-0.1),
    ),
    Asset(
        id = "11",
        symbol = "STETH",
        name = "Lido Staked ETH",
        priceUsd = BigDecimal.valueOf(1789.9276596007142),
        changePercent24Hr = BigDecimal.valueOf(-0.393064843840349),
    ),
)

val baseCoinResponse = AssetResponse(
    data = assets,
)

val uiCoinsTopFive = persistentListOf(
    CoinUIModel(
        id = "6",
        name = "Polkadot",
        symbol = "DOT",
        price = "€22.73",
        changePercent24Hr = "+3.00%",
        changeColor = CoinUIModel.ChangeColors.Positive,
    ),
    CoinUIModel(
        id = "3",
        name = "Litecoin",
        symbol = "LTC",
        price = "€136.36",
        changePercent24Hr = "+2.00%",
        changeColor = CoinUIModel.ChangeColors.Positive,
    ),
    CoinUIModel(
        id = "1",
        name = "Bitcoin",
        symbol = "BTC",
        price = "€45454.55",
        changePercent24Hr = "+1.50%",
        changeColor = CoinUIModel.ChangeColors.Positive,
    ),
    CoinUIModel(
        id = "8",
        name = "Avalanche",
        symbol = "AVAX",
        price = "€72.73",
        changePercent24Hr = "+1.00%",
        changeColor = CoinUIModel.ChangeColors.Positive,
    ),
    CoinUIModel(
        id = "9",
        name = "Dogecoin",
        symbol = "DOGE",
        price = "€0.23",
        changePercent24Hr = "+0.50%",
        changeColor = CoinUIModel.ChangeColors.Positive,
    ),
)
val uiCoinsWorstFive = persistentListOf(
    CoinUIModel(
        id = "7",
        name = "Solana",
        symbol = "SOL",
        price = "€90.91",
        changePercent24Hr = "-2.50%",
        changeColor = CoinUIModel.ChangeColors.Negative,
    ),
    CoinUIModel(
        id = "4",
        name = "Ripple",
        symbol = "XRP",
        price = "€0.91",
        changePercent24Hr = "-1.00%",
        changeColor = CoinUIModel.ChangeColors.Negative,
    ),
    CoinUIModel(
        id = "2",
        name = "Ethereum",
        symbol = "ETH",
        price = "€2727.27",
        changePercent24Hr = "-0.50%",
        changeColor = CoinUIModel.ChangeColors.Negative,
    ),
    CoinUIModel(
        id = "11",
        name = "Lido Staked ETH",
        symbol = "STETH",
        price = "€1627.21",
        changePercent24Hr = "-0.39%",
        changeColor = CoinUIModel.ChangeColors.Negative,
    ),
    CoinUIModel(
        id = "10",
        name = "Shiba Inu",
        symbol = "SHIB",
        price = "€0.00",
        changePercent24Hr = "-0.10%",
        changeColor = CoinUIModel.ChangeColors.Negative,
    ),
)

val currencies = persistentListOf(
    CurrencyUIModel(
        id = "british-pound-sterling",
        name = "british-pound-sterling",
        isSelected = false,
    ),
    CurrencyUIModel(
        id = "euro",
        name = "euro",
        isSelected = true,
    ),
    CurrencyUIModel(
        id = "united-states-dollar",
        name = "united-states-dollar",
        isSelected = false,
    )
)