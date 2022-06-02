package com.example.myfigma.bl

import androidx.compose.ui.res.stringResource
import com.example.myfigma.R
import com.example.myfigma.bl.nanoredux.Action
import com.example.myfigma.bl.nanoredux.Effect
import com.example.myfigma.bl.nanoredux.State
import com.example.myfigma.bl.nanoredux.Store
import com.example.myfigma.demo.cards
import com.example.myfigma.demo.sectionTransactions
import com.example.myfigma.ui.CardDto
import com.example.myfigma.ui.TransactionItemDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class MainState(
    val cards: List<CardDto>,
    val transactions: List<TransactionItemDto>,
    val searchText: String = "",
    val showCardTitleEditDialog: Boolean = false,
    val cardsAmount: Double = 0.00
) : State

sealed class MainAction : Action {
    data class SearchTextChanged(val searchText: String) : MainAction()
    object OpenMenu : MainAction()
    object OpenMessages : MainAction()
    data class OpenChecks(val message: String) : MainAction()
    data class OpenTransfers(val message: String) : MainAction()
    data class OpenCardTitleEditDialog(val showDialog: Boolean) : MainAction()
    data class CardTitleChange(val title: String, val idCard: String) : MainAction()
    data class ShowTransactionsList(val list: List<TransactionItemDto>) : MainAction()
    data class ChangeFavouriteCards(val card: CardDto) : MainAction()
    data class ShowErrorMessage(val message: String) : MainAction()
}

sealed class MainSideEffect : Effect {
    object ShowTodoToast : MainSideEffect()
    class ShowMessageToast(val message: String) : MainSideEffect()
    class ShowErrorMessage(val message: String) : MainSideEffect()
}

class MainStore : Store<MainState, MainAction, MainSideEffect>,
    CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private val state: MutableStateFlow<MainState> =
        MutableStateFlow(
            MainState(
                transactions = sectionTransactions,
                cards = cards,
                cardsAmount = getCardsAmount(cards)
            )
        )

    private val sideEffect = MutableSharedFlow<MainSideEffect>()

    override fun observeState(): StateFlow<MainState> = state

    override fun observeSideEffect(): Flow<MainSideEffect> = sideEffect

    override fun dispatch(action: MainAction) {
        val oldState = state.value
        val newState = when (action) {
            MainAction.OpenMenu -> {
                launch { sideEffect.emit(MainSideEffect.ShowTodoToast) }
                oldState
            }
            MainAction.OpenMessages -> {
                launch { sideEffect.emit(MainSideEffect.ShowTodoToast) }
                oldState
            }
            is MainAction.OpenChecks -> {
                launch { sideEffect.emit(MainSideEffect.ShowMessageToast(action.message)) }
                oldState
            }
            is MainAction.OpenTransfers -> {
                launch { sideEffect.emit(MainSideEffect.ShowMessageToast(action.message)) }
                oldState
            }
            is MainAction.SearchTextChanged -> {
                launch {
                    showFilteredTransactionsList(action.searchText)
                }
                oldState.copy(searchText = action.searchText)
            }
            is MainAction.OpenCardTitleEditDialog -> {
                oldState.copy(showCardTitleEditDialog = action.showDialog)
            }
            is MainAction.CardTitleChange -> {
                val curCards = cards.filter { card -> card.id == action.idCard }
                for (it in curCards) {
                    it.title = action.title
                }
                oldState.copy(cards = cards)
            }
            is MainAction.ShowTransactionsList -> {
                oldState.copy(transactions = action.list)
            }
            is MainAction.ShowErrorMessage -> {
                launch { sideEffect.emit(MainSideEffect.ShowErrorMessage(action.message)) }
                oldState
            }
            is MainAction.ChangeFavouriteCards -> {
                val curCards = cards.filter { card -> card.id == action.card.id }
                for (card in curCards) {
                    if (card.favourite > 0) {
                        card.favourite = 0
                    }
                    else {
                        val favouriteCards = cards.filter { currentCard -> currentCard.favourite > 0 }
                        if(favouriteCards.count() < 2){
                            card.favourite++
                        }
                        else{
                            dispatch(MainAction.ShowErrorMessage("Неможливо вибрати більше двох улюблених карт!"))
                        }
                    }
                }
                val sortedCards = cards.sortedByDescending { it.favourite }
                oldState.copy(cards = sortedCards)
            }
        }
        if (newState != oldState) {
            state.value = newState
        }
    }

    private fun getCardsAmount(cards: List<CardDto>):Double{
        return cards.fold(0.0){previous, item-> previous + item.balanceSum}
    }

    private fun showFilteredTransactionsList(text: String) {
        val filtered = sectionTransactions.filter {
            it.title.contains(text, ignoreCase = true) ||
                    it.iban.contains(text, ignoreCase = true) ||
                    it.sum.contains(text, ignoreCase = true)
        }
        dispatch(MainAction.ShowTransactionsList(filtered))
    }
}