package com.example.myfigma.bl

import com.example.myfigma.bl.nanoredux.Action
import com.example.myfigma.bl.nanoredux.Effect
import com.example.myfigma.bl.nanoredux.State
import com.example.myfigma.bl.nanoredux.Store
import com.example.myfigma.demo.cardsDemo
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
    val cardsAmount: Double = 0.00,
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
    data class ChangeFavouriteCards(val idCard: String) : MainAction()
    data class ShowErrorMessage(val message: String) : MainAction()
}

sealed class MainSideEffect : Effect {
    object ShowTodoToast : MainSideEffect()
    class ShowMessageToast(val message: String) : MainSideEffect()
    class ShowErrorMessage(val message: String) : MainSideEffect()
    class ScrollToCardItem(val cardIndex: Int) : MainSideEffect()
}

class MainStore : Store<MainState, MainAction, MainSideEffect>,
    CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private val state: MutableStateFlow<MainState> =
        MutableStateFlow(
            MainState(
                transactions = sectionTransactions,
                cards = cardsDemo,
                cardsAmount = getCardsAmount(cardsDemo)
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
                val copiedCardsList = oldState.cards.toMutableList()
                val curCard = copiedCardsList.find { card -> card.id == action.idCard }
                if (curCard != null) {
                    val curIndex = copiedCardsList.indexOf(curCard)
                    copiedCardsList[curIndex] = curCard.copy(title = action.title)
                    oldState.copy(cards = copiedCardsList)
                } else {
                    oldState
                }
            }
            is MainAction.ShowTransactionsList -> {
                oldState.copy(transactions = action.list)
            }
            is MainAction.ShowErrorMessage -> {
                launch { sideEffect.emit(MainSideEffect.ShowErrorMessage(action.message)) }
                oldState
            }
            is MainAction.ChangeFavouriteCards -> {
                val sortedCards = getSortedCardsList(oldState.cards.toMutableList(), action.idCard)
                oldState.copy(cards = sortedCards)
            }
        }
        if (newState != oldState) {
            state.value = newState
        }
    }

    private fun getSortedCardsList(copiedCardsList: MutableList<CardDto>, idCard: String) : List<CardDto>{
        val curCard = copiedCardsList.find { card -> card.id == idCard }
        val curCardIndex = copiedCardsList.indexOf(curCard)
        if (curCard != null) {
            if (curCard.favourite) {
                copiedCardsList[curCardIndex] = curCard.copy(favourite = false)
            } else {
                val favouriteCards =
                    copiedCardsList.filter { currentCard -> currentCard.favourite }
                if (favouriteCards.count() < 2) {
                    copiedCardsList[curCardIndex] = curCard.copy(favourite = true)
                } else {
                    dispatch(MainAction.ShowErrorMessage("Неможливо вибрати більше двох улюблених карт!"))
                }
            }
        }
        val sortedCards = copiedCardsList.sortedByDescending { it.favourite }
        val cardIndexAfterSort =
            sortedCards.indexOf(sortedCards.find { card -> card.id == idCard })
        if (cardIndexAfterSort != curCardIndex) {
            launch { sideEffect.emit(MainSideEffect.ScrollToCardItem(cardIndexAfterSort)) }
        }
        return sortedCards
    }

    private fun getCardsAmount(cards: List<CardDto>): Double {
        return cards.fold(0.0) { previous, item -> previous + item.balanceSum }
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