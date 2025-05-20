package at.sfraungruber.assetbrowser.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class to fetch and persist user preferences.
 * Current implementation only supports a hard-coded preference, but in the future this should
 * be extended to persist user preferences.
 */
@Singleton
class UserPreferences
@Inject
constructor() {
    private val _preferredCurrency = MutableStateFlow(Currency.Euro)

    val preferredCurrency: Flow<Currency> = _preferredCurrency

    fun setPreferredCurrency(currency: Currency) {
        _preferredCurrency.value = currency
    }
}
