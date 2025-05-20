package at.sfraungruber.assetbrowser.ui.shared

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import at.sfraungruber.assetbrowser.ui.coins.CoinUIModel
import at.sfraungruber.assetbrowser.ui.coins.CoinsScreen
import at.sfraungruber.assetbrowser.ui.coins.CoinsUiList
import at.sfraungruber.assetbrowser.ui.coins.CoinsViewModel
import at.sfraungruber.assetbrowser.ui.coins.CurrencyUIModel
import kotlinx.collections.immutable.persistentListOf

class CoinsScreenScreenshots {
    private val dataState = CoinsViewModel.State.Data(
        isLoading = false,
        coins = persistentListOf(
            CoinsUiList(
                title = "Gainers",
                assets = persistentListOf(
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
                ),
            ),
            CoinsUiList(
                title = "Gainers",
                assets = persistentListOf(),
            )
        ),
        currencies = persistentListOf(
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
        ),
    )

    @Preview(showBackground = true)
    @Composable
    fun DataState() {
        AppTheme {
            CoinsScreen(
                dataState,
                onRefresh = {},
                onCurrencySelected = {},
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ErrorState() {
        AppTheme {
            CoinsScreen(
                CoinsViewModel.State.Error,
                onRefresh = {},
                onCurrencySelected = {},
            )
        }
    }
}
