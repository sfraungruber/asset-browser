package at.sfraungruber.assetbrowser.ui.coins

import android.content.Context
import at.sfraungruber.assetbrowser.R
import at.sfraungruber.assetbrowser.assets
import at.sfraungruber.assetbrowser.baseCoinResponse
import at.sfraungruber.assetbrowser.currency
import at.sfraungruber.assetbrowser.data.Currency.Euro
import at.sfraungruber.assetbrowser.data.UserPreferences
import at.sfraungruber.assetbrowser.data.repository.CoinRepository
import at.sfraungruber.assetbrowser.data.repository.CurrencyRepository
import at.sfraungruber.assetbrowser.uiCoinsTopFive
import at.sfraungruber.assetbrowser.uiCoinsWorstFive
import at.sfraungruber.assetbrowser.utils.FormattingUtils
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class FormatAssetListUseCaseTest {
    private val appContext =
        mockk<Context> {
            every { getString(R.string.gainers) } returns "Gainers"
            every { getString(R.string.losers) } returns "Losers"
        }

    private val coinRepository: CoinRepository = mockk()
    private val currencyRepository: CurrencyRepository = mockk()

    private val userPreferences: UserPreferences = mockk()

    private val formatAssetListUseCase: FormatAssetListUseCase =
        FormatAssetListUseCase(
            appContext,
            FormattingUtils(appContext),
        )

    @Test
    fun `When call UseCase Then expected coins are returned`() = runTest {
        // Given
        val numberOfCoins = 3
        every { userPreferences.preferredCurrency } returns flowOf(Euro)
        coEvery { coinRepository.getCoins() } returns
            baseCoinResponse.copy(
                // shuffle data to test sorting
                data = baseCoinResponse.data.shuffled(),
            )
        coEvery { currencyRepository.getCurrencyConversion(currency = Euro) } returns currency

        // When
        val result =
            formatAssetListUseCase(
                assets = assets,
                currency = currency,
                numberOfCoins = numberOfCoins,
            )

        // Then
        assertEquals(
            listOf(
                CoinsUiList(
                    title = "Gainers",
                    assets = uiCoinsTopFive.take(numberOfCoins).toImmutableList(),
                ),
                CoinsUiList(
                    title = "Losers",
                    assets = uiCoinsWorstFive.take(numberOfCoins).toImmutableList(),
                ),
            ),
            result,
        )
    }
}
