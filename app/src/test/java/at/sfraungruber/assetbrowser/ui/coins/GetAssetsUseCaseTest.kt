package at.sfraungruber.assetbrowser.ui.coins

import at.sfraungruber.assetbrowser.data.repository.CoinRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetAssetsUseCaseTest {
    // TODO: replace with JUnit Rule
    private val testDispatcher = StandardTestDispatcher()

    private val coinsRepository: CoinRepository = mockk()

    private val getCoinsUseCase =
        GetAssetsUseCase(
            coinsRepository = coinsRepository,
            ioDispatcher = testDispatcher,
        )

    @Test(expected = IllegalArgumentException::class)
    fun `When repository throws exception Then exception is rethrown`() = runTest(testDispatcher) {
        // Given
        coEvery { coinsRepository.getCoins() } throws IllegalArgumentException("test exception")

        // When
        getCoinsUseCase()

        // Then
        // exception is thrown
    }
}
