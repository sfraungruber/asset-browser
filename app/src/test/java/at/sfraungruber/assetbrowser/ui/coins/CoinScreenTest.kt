package at.sfraungruber.assetbrowser.ui.coins

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import androidx.test.platform.app.InstrumentationRegistry
import at.sfraungruber.assetbrowser.R
import kotlinx.collections.immutable.persistentListOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CoinScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    private val dataState =
        CoinsViewModel.State.Data(
            isLoading = false,
            currencies =
                persistentListOf(
                    CurrencyUIModel(
                        id = "euro",
                        name = "Euro",
                        isSelected = true,
                    ),
                ),
            coins =
                persistentListOf(
                    CoinsUiList(
                        title = "Gainers",
                        assets =
                            persistentListOf(
                                CoinUIModel(
                                    id = "6",
                                    name = "Winner #1",
                                    symbol = "W1",
                                    price = "€12",
                                    changePercent24Hr = "+10.00%",
                                    changeColor = CoinUIModel.ChangeColors.Positive,
                                ),
                            ),
                    ),
                    CoinsUiList(
                        title = "Losers",
                        assets =
                            persistentListOf(
                                CoinUIModel(
                                    id = "6",
                                    name = "Loser #1",
                                    symbol = "L1",
                                    price = "€21",
                                    changePercent24Hr = "-10.00%",
                                    changeColor = CoinUIModel.ChangeColors.Negative,
                                ),
                            ),
                    ),
                ),
        )

    @Test
    fun testCoinListTabSwitch() {
        // Given
        composeTestRule.setContent {
            CoinsScreen(
                state = dataState,
                onRefresh = { },
                onCurrencySelected = { },
            )
        }
        composeTestRule.onNodeWithText("Winner #1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Loser #1").assertIsNotDisplayed()

        // When
        composeTestRule
            .onNodeWithText(context.getString(R.string.losers))
            .performClick()

        // Then
        composeTestRule.onNodeWithText("Winner #1").assertIsNotDisplayed()
        composeTestRule.onNodeWithText("Loser #1").assertIsDisplayed()
    }

    @Test
    fun testPullToRefresh() {
        // Given
        var onRefreshCalled = false
        composeTestRule.setContent {
            CoinsScreen(
                state = dataState,
                onRefresh = {
                    onRefreshCalled = true
                },
                onCurrencySelected = { },
            )
        }

        // When
        composeTestRule.onNode(hasContentDescription("CoinList"))
            .performTouchInput { swipeDown() }

        composeTestRule.waitForIdle()

        // Then
        assert(onRefreshCalled)
    }

    @Test
    fun testErrorStateRetryButton() {
        // Given
        var retryButtonClicked = false
        composeTestRule.setContent {
            CoinsScreen(
                state = CoinsViewModel.State.Error,
                onRefresh = { retryButtonClicked = true },
                onCurrencySelected = { },
            )
        }

        // When
        composeTestRule
            .onNodeWithText(context.getString(R.string.retry))
            .performClick()

        // Then
        assert(retryButtonClicked)
    }
}
