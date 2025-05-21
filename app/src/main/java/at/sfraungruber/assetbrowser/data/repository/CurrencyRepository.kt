package at.sfraungruber.assetbrowser.data.repository

import at.sfraungruber.assetbrowser.data.Currency
import at.sfraungruber.assetbrowser.data.remote.CurrencyApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepository @Inject constructor(
    private val currencyApi: CurrencyApi,
) {
    suspend fun getCurrencyConversion(currency: Currency) = currencyApi.getRateById(
        id = currency.id,
    ).data
}
