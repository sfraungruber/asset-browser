package at.sfraungruber.assetbrowser.ui.coins

import at.sfraungruber.assetbrowser.data.remote.Currency
import at.sfraungruber.assetbrowser.data.repository.CurrencyRepository
import at.sfraungruber.assetbrowser.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * UseCase to fetch data from the repositories and format it to show the data on the UI.
 */
@Singleton
class GetCurrencyUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(currency: at.sfraungruber.assetbrowser.data.Currency): Currency = withContext(ioDispatcher) {
        return@withContext currencyRepository.getCurrencyConversion(
            currency = currency,
        )
    }
}
