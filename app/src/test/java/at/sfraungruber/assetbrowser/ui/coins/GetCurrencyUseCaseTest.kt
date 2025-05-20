package at.sfraungruber.assetbrowser.ui.coins

import at.sfraungruber.assetbrowser.data.Currency.Euro
import at.sfraungruber.assetbrowser.data.repository.CurrencyRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetCurrencyUseCaseTest {
    private val testDispatcher = StandardTestDispatcher()

    private val currencyRepository: CurrencyRepository = mockk()

    private val getCurrencyUseCase = GetCurrencyUseCase(
        currencyRepository = currencyRepository,
        ioDispatcher = testDispatcher,
    )

    @Test(expected = IllegalArgumentException::class)
    fun `When repository throws exception Then exception is rethrown`() =
        runTest(testDispatcher) {
            // Given
            coEvery { currencyRepository.getCurrencyConversion(Euro) } throws IllegalArgumentException(
                "test exception"
            )

            // When
            getCurrencyUseCase(Euro)

            // Then
            // exception is thrown
        }
}
