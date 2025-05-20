package at.sfraungruber.assetbrowser.ui.coins

import android.content.Context
import at.sfraungruber.assetbrowser.R
import at.sfraungruber.assetbrowser.data.remote.Asset
import at.sfraungruber.assetbrowser.data.remote.Currency
import at.sfraungruber.assetbrowser.utils.FormattingUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.collections.immutable.toImmutableList
import java.math.RoundingMode
import javax.inject.Inject
import javax.inject.Singleton

/**
 * UseCase to fetch data from the repositories and format it to show the data on the UI.
 */
@Singleton
class FormatAssetListUseCase
@Inject
constructor(
    @ApplicationContext private val appContext: Context,
    private val valueFormatter: FormattingUtils,
) {
    operator fun invoke(
        assets: List<Asset>,
        currency: Currency,
        numberOfCoins: Int = 15,
    ): List<CoinsUiList> {
        val sortedCoins = assets.sortedByDescending { it.changePercent24Hr }

        return listOf(
            CoinsUiList(
                title = appContext.getString(R.string.gainers),
                assets = sortedCoins
                    .take(numberOfCoins)
                    .mapToCoinUi(currency)
                    .toImmutableList(),
            ),
            CoinsUiList(
                title = appContext.getString(R.string.losers),
                assets = sortedCoins
                    .takeLast(numberOfCoins)
                    .reversed()
                    .mapToCoinUi(currency)
                    .toImmutableList(),
            )
        )
    }

    private fun List<Asset>.mapToCoinUi(currency: Currency): List<CoinUIModel> =
        map {
            CoinUIModel(
                id = it.id,
                name = it.name,
                symbol = it.symbol,
                price =
                    valueFormatter.formatCurrency(
                        value =
                            it.priceUsd.divide(
                                /* divisor = */ currency.rateUsd,
                                /* scale = */ 2,
                                /* roundingMode = */ RoundingMode.HALF_UP,
                            ).toDouble(),
                        currencySymbol = currency.currencySymbol,
                    ),
                changePercent24Hr =
                    valueFormatter.formatValueChange(
                        value = it.changePercent24Hr.toDouble(),
                    ),
                changeColor =
                    if (it.changePercent24Hr.toDouble() < 0) {
                        CoinUIModel.ChangeColors.Negative
                    } else {
                        CoinUIModel.ChangeColors.Positive
                    },
            )
        }
}
