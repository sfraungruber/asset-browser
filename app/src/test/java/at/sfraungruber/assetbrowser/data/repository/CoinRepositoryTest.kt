package at.sfraungruber.assetbrowser.data.repository

import at.sfraungruber.assetbrowser.baseCoinResponse
import at.sfraungruber.assetbrowser.data.remote.CryptoApi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CoinRepositoryTest {
    private val mockApi = mockk<CryptoApi>()
    private val repository = CoinRepository(
        cryptoApi = mockApi,
    )

    @Test
    fun `Given API returns response When get coins from repository Then expected response`() =
        runTest {
            // Given
            coEvery { mockApi.getAsset() } returns baseCoinResponse

            // When
            val result = repository.getCoins()

            // Then
            assertEquals(
                baseCoinResponse,
                result,
            )
        }

    @Test(expected = IllegalArgumentException::class)
    fun `When API throws exception Then exception is mapped`() =
        runTest {
            // Given
            coEvery { mockApi.getAsset() } throws IllegalArgumentException("some exception")

            // When
            repository.getCoins()

            // Then
            // exception is thrown
        }
}
