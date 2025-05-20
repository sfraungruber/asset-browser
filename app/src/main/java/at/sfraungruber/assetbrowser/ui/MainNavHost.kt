package at.sfraungruber.assetbrowser.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import at.sfraungruber.assetbrowser.ui.coins.CoinsScreen
import at.sfraungruber.assetbrowser.ui.coins.CoinsViewModel
import kotlinx.serialization.Serializable

@Serializable
data class CoinScreen(
    val name: String,
)

@Composable
fun CoinNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = CoinScreen("euro"),
    ) {
        composable<CoinScreen> {
            val viewModel: CoinsViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()

            CoinsScreen(
                state = state,
                onRefresh = {
                    viewModel.onEvent(CoinsViewModel.UserEvent.Load)
                },
                onCurrencySelected = {
                    viewModel.onEvent(
                        CoinsViewModel.UserEvent.PreferredCurrencySelected(it)
                    )
                }
            )
        }
    }
}
