package at.sfraungruber.assetbrowser.ui.coins

import at.sfraungruber.assetbrowser.data.Currency
import at.sfraungruber.assetbrowser.data.UserPreferences
import at.sfraungruber.assetbrowser.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * UseCase to fetch data from the repositories and format it to show the data on the UI.
 */
@Singleton
class SetPreferredCurrencyUseCase @Inject constructor(
    private val userPreferences: UserPreferences,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(currency: Currency): Unit = withContext(ioDispatcher) {
        userPreferences.setPreferredCurrency(
            currency = currency,
        )
    }
}
