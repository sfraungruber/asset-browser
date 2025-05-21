package at.sfraungruber.assetbrowser.utils

import android.content.Context
import at.sfraungruber.assetbrowser.R
import at.sfraungruber.assetbrowser.data.Currency
import at.sfraungruber.assetbrowser.data.Currency.BritishPoundSterling
import at.sfraungruber.assetbrowser.data.Currency.Euro
import at.sfraungruber.assetbrowser.data.Currency.USD
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.DecimalFormat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FormattingUtils @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun formatCurrency(
        value: Double,
        currencySymbol: String,
    ): String = DecimalFormat("0.00").apply {
        positivePrefix = currencySymbol
        negativePrefix = currencySymbol
    }.format(value)

    fun formatValueChange(value: Double): String = DecimalFormat("0.00").apply {
        negativePrefix = "-"
        positivePrefix = "+"
        negativeSuffix = "%"
        positiveSuffix = "%"
    }.format(value)

    fun format(currency: Currency): String = when (currency) {
        BritishPoundSterling -> context.getString(R.string.currency_gbp)
        Euro -> context.getString(R.string.currency_eur)
        USD -> context.getString(R.string.currency_usd)
    }
}
