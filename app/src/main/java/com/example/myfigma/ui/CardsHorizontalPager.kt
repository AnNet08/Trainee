package com.example.myfigma.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myfigma.bl.MainAction
import com.example.myfigma.bl.MainSideEffect
import com.example.myfigma.bl.MainState
import com.example.myfigma.ui.theme.Secondary
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ShowHorizontalPager(
    state: MainState,
    sideEffect: Flow<MainSideEffect>,
    dispatch: (MainAction) -> Unit
) {
    val pagerState = rememberPagerState()
    val cards = state.cards.toList()
    HorizontalPager(
        count = cards.count(),
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 32.dp),
    ) { currentCard ->
        ShowCardConstraint(dispatch, cards[currentCard])
    }
    LaunchedEffect(Unit) {
        sideEffect.collect { effect ->
            if (effect is MainSideEffect.ScrollToCardItem) {
                pagerState.animateScrollToPage(effect.cardIndex)
            }
        }
    }
    CardTitleEditDialog(card = cards[pagerState.currentPage], state = state, dispatch = dispatch)
    HorizontalPagerIndicator(
        pagerState = pagerState,
        activeColor = Secondary,
        modifier = Modifier
            .padding(16.dp),
    )
}
