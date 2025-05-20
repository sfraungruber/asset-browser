package at.sfraungruber.assetbrowser.ui.coins

import at.sfraungruber.assetbrowser.data.Currency
import at.sfraungruber.assetbrowser.data.UserPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * UseCase to fetch data from the repositories and format it to show the data on the UI.
 */
@Singleton
class GetPreferredCurrencyFlowUseCase
@Inject
constructor(
    private val userPreferences: UserPreferences,
) {
    operator fun invoke(): Flow<Currency> = userPreferences.preferredCurrency
}
