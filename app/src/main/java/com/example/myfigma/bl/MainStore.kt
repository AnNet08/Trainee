package com.example.myfigma.bl

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
    val showCardTitleEditDialog: Boolean = false
) : State

sealed class MainAction : Action {
    data class SearchTextChanged(val searchText: String) : MainAction()
    object OpenMenu : MainAction()
    object OpenMessages : MainAction()
    data class OpenChecks(val message: String) : MainAction()
    data class OpenTransfers(val message: String) : MainAction()
    data class OpenCardTitleEditDialog(val showDialog: Boolean) : MainAction()
    //data class CardTitleChange(val title: String, val idCard: String) : MainAction()
    data class CardTitleChange(val title: String, val idCard: Int) : MainAction()
}

sealed class MainSideEffect : Effect {
    object ShowTodoToast : MainSideEffect()
    class ShowMessageToast(val message: String) : MainSideEffect()
}

class MainStore : Store<MainState, MainAction, MainSideEffect>,
    CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private val state: MutableStateFlow<MainState> =
        MutableStateFlow(
            MainState(
                transactions = sectionTransactions,
                cards = cards
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
                oldState.copy(searchText = action.searchText)
            }
            is MainAction.OpenCardTitleEditDialog -> {
                oldState.copy(showCardTitleEditDialog = action.showDialog)
            }
            is MainAction.CardTitleChange -> {
                val curCards = oldState.cards.toList()
                curCards[action.idCard].title = action.title
                oldState.copy(cards = curCards)
            }
        }
        if (newState != oldState) {
            state.value = newState
        }
    }
}