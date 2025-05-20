package at.sfraungruber.assetbrowser.ui.coins

import kotlinx.collections.immutable.ImmutableList

data class CoinsUiList(
    val title: String,
    val assets: ImmutableList<CoinUIModel>,
)

data class CoinUIModel(
    val id: String,
    val name: String,
    val symbol: String,
    val price: String,
    val changePercent24Hr: String,
    val changeColor: ChangeColors,
) {
    enum class ChangeColors {
        Positive,
        Negative,
    }
}
