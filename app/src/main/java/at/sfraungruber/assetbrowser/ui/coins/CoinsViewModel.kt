package at.sfraungruber.assetbrowser.ui.coins

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.sfraungruber.assetbrowser.data.Currency
import at.sfraungruber.assetbrowser.utils.FormattingUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

/**
 * Fetches the crypto currencies from the network and exposes the data as state to the UI.
 */
@HiltViewModel
class CoinsViewModel
@Inject
constructor(
    private val getAssetsUseCase: GetAssetsUseCase,
    getPreferredCurrencyFlowUseCase: GetPreferredCurrencyFlowUseCase,
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val formatAssetListUseCase: FormatAssetListUseCase,
    private val setPreferredCurrencyUseCase: SetPreferredCurrencyUseCase,
    private val valueFormatter: FormattingUtils,
) : ViewModel() {

    private var assets: ImmutableList<CoinsUiList>? = null

    private val exceptionHandlingScope =
        viewModelScope +
                CoroutineExceptionHandler { _, _ ->
                    assets = null
                    _state.tryEmit(State.Error)
                }

    private val selectedCurrencyFlow = getPreferredCurrencyFlowUseCase()
    private val reloadTrigger = Channel<Unit>(capacity = Channel.CONFLATED)

    private val _state = MutableStateFlow(State.Initial)
    val state: StateFlow<State> = _state.asStateFlow()

    private val loadDataFlow = combine(
        selectedCurrencyFlow,
        reloadTrigger.consumeAsFlow(),
    ) { selectedCurrency, _ ->
        selectedCurrency
    }.onEach(::loadData)

    init {
        loadDataFlow.launchIn(viewModelScope)
        reloadTrigger.trySend(Unit)
    }

    private fun loadData(selectedCurrency: Currency) =
        exceptionHandlingScope.launch {
            val currencies = Currency.entries.map {
                CurrencyUIModel(
                    id = it.id,
                    name = valueFormatter.format(it),
                    isSelected = it.id == selectedCurrency.id,
                )
            }.toPersistentList()

            _state.value = State.Data(
                isLoading = true,
                coins = assets,
                currencies = currencies,
            )

            val deferredSortedCoins = async { getAssetsUseCase() }
            val deferredCurrency = async { getCurrencyUseCase(selectedCurrency) }

            // map currency conversion and assets to UI state
            assets = formatAssetListUseCase(
                assets = deferredSortedCoins.await(),
                currency = deferredCurrency.await(),
            ).toPersistentList()

            _state.value = State.Data(
                isLoading = false,
                coins = assets,
                currencies = currencies,
            )
        }

    fun onEvent(event: UserEvent) =
        viewModelScope.launch {
            when (event) {
                UserEvent.Load -> reloadTrigger.send(Unit)

                is UserEvent.PreferredCurrencySelected -> {
                    Currency.entries.firstOrNull {
                        it.id == event.currency.id
                    }?.let { setPreferredCurrencyUseCase(it) }
                }
            }
        }

    sealed interface State {

        data class Data(
            val isLoading: Boolean,
            val coins: ImmutableList<CoinsUiList>?,
            val currencies: ImmutableList<CurrencyUIModel>?,
        ) : State

        data object Error : State


        companion object {
            val Initial: State = Data(
                isLoading = true,
                currencies = null,
                coins = null,
            )
        }
    }

    sealed interface UserEvent {
        data object Load : UserEvent
        data class PreferredCurrencySelected(
            val currency: CurrencyUIModel,
        ) : UserEvent
    }
}
