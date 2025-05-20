package at.sfraungruber.assetbrowser.data.repository

import at.sfraungruber.assetbrowser.currency
import at.sfraungruber.assetbrowser.currencyResponse
import at.sfraungruber.assetbrowser.data.Currency
import at.sfraungruber.assetbrowser.data.remote.CurrencyApi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CurrencyRepositoryTest {
    private val mockApi = mockk<CurrencyApi>()
    private val repository = CurrencyRepository(
        currencyApi = mockApi,
    )

    @Test
    fun `Given API returns response When get coins from repository Then expected response`() =
        runTest {
            // Given
            coEvery { mockApi.getRateById("euro") } returns currencyResponse

            // When
            val result = repository.getCurrencyConversion(Currency.Euro)

            // Then
            assertEquals(
                currency,
                result,
            )
        }

    @Test(expected = IllegalArgumentException::class)
    fun `When API throws exception Then exception is mapped`() =
        runTest {
            // Given
            coEvery { mockApi.getRateById() } throws IllegalArgumentException("some exception")

            // When
            repository.getCurrencyConversion(Currency.Euro)

            // Then
            // exception is thrown
        }
}
