package at.sfraungruber.assetbrowser.ui.coins

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import at.sfraungruber.assetbrowser.R
import at.sfraungruber.assetbrowser.ui.coins.CoinUIModel.ChangeColors.Negative
import at.sfraungruber.assetbrowser.ui.coins.CoinUIModel.ChangeColors.Positive
import at.sfraungruber.assetbrowser.ui.shared.AppTheme
import at.sfraungruber.assetbrowser.ui.shared.LocalAppThemeColors
import at.sfraungruber.assetbrowser.ui.shared.Spaces
import at.sfraungruber.assetbrowser.ui.shared.Tab
import at.sfraungruber.assetbrowser.ui.shared.TabbedComponent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Screen to show the best and worst performing coins in a list. The user can switch between the two
 * lists with a chip component on the top.
 */
@Composable
fun CoinsScreen(
    state: CoinsViewModel.State,
    onRefresh: () -> Unit,
    onCurrencySelected: (CurrencyUIModel) -> Unit,
) {
    when (state) {
        CoinsViewModel.State.Error ->
            ErrorState(
                onRetry = onRefresh,
            )

        is CoinsViewModel.State.Data -> {
            TabbedCoinsList(
                data = state,
                onRefresh = onRefresh,
                onCurrencySelected = onCurrencySelected,
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TabbedCoinsList(
    data: CoinsViewModel.State.Data,
    onRefresh: () -> Unit,
    onCurrencySelected: (CurrencyUIModel) -> Unit,
) {
    var showCurrencySelection by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {

            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.coins)
                    )
                },
                actions = {
                    Button(
                        onClick = {
                            showCurrencySelection = true
                        }
                    ) {
                        data.currencies?.firstOrNull { it.isSelected }.let {
                            Text(
                                stringResource(
                                    R.string.selected_currency,
                                    it?.name ?: "-",
                                )
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = showCurrencySelection,
                        onDismissRequest = { showCurrencySelection = true },
                    ) {
                        data.currencies?.forEach {
                            DropdownMenuItem(
                                modifier = Modifier.background(
                                    if (it.isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                                ),
                                text = { Text(it.name) },
                                onClick = {
                                    onCurrencySelected(it)
                                    showCurrencySelection = false
                                },
                            )
                        }
                    }
                }
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
        ) {
            PullToRefreshBox(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .semantics { contentDescription = "PullToRefresh" },
                isRefreshing = data.isLoading,
                onRefresh = onRefresh,
            ) {
                data.coins?.map {
                    val listState = rememberSaveable(
                        key = it.title,
                        saver = LazyListState.Saver,
                    ) {
                        LazyListState(
                            firstVisibleItemIndex = 0,
                            firstVisibleItemScrollOffset = 0
                        )
                    }

                    rememberLazyListState()

                    Tab(it.title) {
                        CoinList(
                            coins = it.assets,
                            listState = listState,
                        )
                    }
                }?.takeIf { it.isNotEmpty() }?.let { tabs ->
                    TabbedComponent(*tabs.toTypedArray())
                }
            }
        }
    }
}

@Composable
private fun ErrorState(
    onRetry: () -> Unit,
) {
    Surface {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(Spaces.ScreenPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.panda_stop),
                contentDescription = "",
            )
            Spacer(modifier = Modifier.height(Spaces.Large))
            Text(
                stringResource(R.string.common_error_title),
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                stringResource(R.string.common_error_detail),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(Spaces.Large))
            Button(onClick = onRetry) {
                Text(stringResource(R.string.retry))
            }
        }
    }
}

@Composable
private fun CoinList(
    coins: ImmutableList<CoinUIModel>,
    listState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        state = listState,
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = Spaces.ScreenPadding)
                .semantics { contentDescription = "CoinList" },
        verticalArrangement = Arrangement.spacedBy(Spaces.Large),
        contentPadding = PaddingValues(top = Spaces.Medium)
    ) {
        items(coins, key = { it.id }) {
            CoinItem(it)
        }
    }
}

@Composable
private fun CoinItem(coin: CoinUIModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .semantics { contentDescription = "CoinItem" },
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .weight(1F),
        ) {
            Text(
                text = coin.name,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = coin.symbol,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Column(
            horizontalAlignment = Alignment.End,
        ) {
            Text(
                text = coin.changePercent24Hr,
                style = MaterialTheme.typography.bodyMedium,
                color = when (coin.changeColor) {
                    Positive -> LocalAppThemeColors.current.positive
                    Negative -> LocalAppThemeColors.current.negative
                },
            )
            Text(
                text = coin.price,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CoinsScreenDataPreview(
) {
    AppTheme {
        CoinsScreen(
            state = CoinsViewModel.State.Data(
                isLoading = false,
                currencies = persistentListOf(
                    CurrencyUIModel(
                        id = "euro",
                        name = "Euro",
                        isSelected = true,
                    )
                ),
                coins = persistentListOf(
                    CoinsUiList(
                        title = "List #1",
                        assets = persistentListOf(
                            CoinUIModel(
                                id = "btc",
                                name = "Bitcoin",
                                symbol = "BTC",
                                price = "6459.34",
                                changePercent24Hr = "+4.44%",
                                changeColor = Positive,
                            ),
                            CoinUIModel(
                                id = "abc",
                                name = "Alphabet Coin",
                                symbol = "ABC",
                                price = "12.02",
                                changePercent24Hr = "-1.21%",
                                changeColor = Negative,
                            ),
                        ),
                    ),
                    CoinsUiList(
                        title = "List #2",
                        assets = persistentListOf(),
                    ),
                    CoinsUiList(
                        title = "List #3",
                        assets = persistentListOf(),
                    ),
                ),
            ),
            onRefresh = {},
            onCurrencySelected = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CoinsScreenErrorPreview(
) {
    AppTheme {
        CoinsScreen(
            state = CoinsViewModel.State.Error,
            onRefresh = {},
            onCurrencySelected = {},
        )
    }
}
