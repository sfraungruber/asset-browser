package at.sfraungruber.assetbrowser.ui.coins

import app.cash.turbine.test
import at.sfraungruber.assetbrowser.assets
import at.sfraungruber.assetbrowser.currencies
import at.sfraungruber.assetbrowser.currency
import at.sfraungruber.assetbrowser.data.Currency
import at.sfraungruber.assetbrowser.uiCoinsTopFive
import at.sfraungruber.assetbrowser.utils.FormattingUtils
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class CoinsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getAssetsUseCase = mockk<GetAssetsUseCase>()
    private val getPreferredCurrencyFlowUseCase =
        mockk<GetPreferredCurrencyFlowUseCase> {
            every { this@mockk.invoke() } returns MutableStateFlow(Currency.Euro)
        }
    private val getCurrencyUseCase = mockk<GetCurrencyUseCase>()
    private val formatAssetListUseCase = mockk<FormatAssetListUseCase>()
    private val setPreferredCurrencyUseCase = mockk<SetPreferredCurrencyUseCase>()
    private val valueFormatter =
        mockk<FormattingUtils> {
            every { format(any()) } answers {
                (it.invocation.args.first() as Currency).id
            }
        }

    private val expectedAssets =
        persistentListOf(
            CoinsUiList(
                title = "top",
                assets = uiCoinsTopFive,
            ),
            CoinsUiList(
                title = "worst",
                assets = uiCoinsTopFive,
            ),
        )

    @After
    fun cleanup() {
        clearAllMocks()
    }

    @Test
    fun `Given useCase returns data When viewModel created Then coins are returned as expected`() = runTest {
        // Given
        coEvery { getAssetsUseCase() } returns assets
        coEvery { getPreferredCurrencyFlowUseCase() } returns flowOf(Currency.Euro)
        coEvery { getCurrencyUseCase(Currency.Euro) } returns currency
        coEvery { formatAssetListUseCase(assets, currency) } returns expectedAssets

        // When
        val viewModel = createViewModel()

        // Then
        viewModel.state.test {
            assertEquals(
                CoinsViewModel.State.Initial,
                awaitItem(),
            )

            assertEquals(
                CoinsViewModel.State.Data(
                    isLoading = true,
                    coins = null,
                    currencies = currencies,
                ),
                awaitItem(),
            )

            assertEquals(
                CoinsViewModel.State.Data(
                    isLoading = false,
                    coins = expectedAssets,
                    currencies = currencies,
                ),
                awaitItem(),
            )
        }
    }

    @Test
    fun `Given UseCase throws exception first time When load Then coins are returned after initial error`() = runTest {
        // Given
        var count = 0
        coEvery { getAssetsUseCase() } returns assets
        coEvery { getPreferredCurrencyFlowUseCase() } returns flowOf(Currency.Euro)
        coEvery { getCurrencyUseCase(Currency.Euro) } answers {
            when (count++) {
                0 -> throw IllegalStateException("test exception")
                else -> currency
            }
        }
        coEvery { formatAssetListUseCase(assets, currency) } returns expectedAssets

        // When
        val viewModel = createViewModel()

        // Then
        viewModel.state.test {
            assertEquals(
                CoinsViewModel.State.Initial,
                awaitItem(),
            )

            assertEquals(
                CoinsViewModel.State.Data(
                    isLoading = true,
                    coins = null,
                    currencies = currencies,
                ),
                awaitItem(),
            )

            assertEquals(
                CoinsViewModel.State.Error,
                awaitItem(),
            )

            viewModel.onEvent(CoinsViewModel.UserEvent.Load)

            assertEquals(
                CoinsViewModel.State.Data(
                    isLoading = true,
                    coins = null,
                    currencies = currencies,
                ),
                awaitItem(),
            )

            assertEquals(
                CoinsViewModel.State.Data(
                    isLoading = false,
                    coins = expectedAssets,
                    currencies = currencies,
                ),
                awaitItem(),
            )
        }
    }

    private fun createViewModel(): CoinsViewModel {
        return CoinsViewModel(
            getAssetsUseCase = getAssetsUseCase,
            getCurrencyUseCase = getCurrencyUseCase,
            getPreferredCurrencyFlowUseCase = getPreferredCurrencyFlowUseCase,
            formatAssetListUseCase = formatAssetListUseCase,
            setPreferredCurrencyUseCase = setPreferredCurrencyUseCase,
            valueFormatter = valueFormatter,
        )
    }
}
