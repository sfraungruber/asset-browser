package at.sfraungruber.assetbrowser.data

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class UserPreferencesTest {
    private val userPreferences = UserPreferences()

    @Test
    fun testPreferredCurrency() = runTest {
        userPreferences.preferredCurrency.test {
            assertEquals(
                Currency.Euro,
                awaitItem(),
            )
        }
    }
}
