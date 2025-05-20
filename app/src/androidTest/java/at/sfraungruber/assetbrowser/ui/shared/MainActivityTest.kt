package at.sfraungruber.assetbrowser.ui.shared

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import at.sfraungruber.assetbrowser.ui.MainActivity
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Test
    fun testCoinListLoaded() {
        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onNodeWithContentDescription("CoinList").isDisplayed()
        }

        composeTestRule.onAllNodesWithContentDescription("CoinItem")
            .onFirst().assertIsDisplayed()
    }
}
