package at.sfraungruber.assetbrowser.ui.coins

import at.sfraungruber.assetbrowser.data.remote.Asset
import at.sfraungruber.assetbrowser.data.repository.CoinRepository
import at.sfraungruber.assetbrowser.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * UseCase to fetch data from the repositories and format it to show the data on the UI.
 */
@Singleton
class GetAssetsUseCase
@Inject
constructor(
    private val coinsRepository: CoinRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(): List<Asset> =
        withContext(ioDispatcher) {
            return@withContext coinsRepository.getCoins().data
        }
}
