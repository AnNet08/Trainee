package com.example.myfigma.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myfigma.bl.MainAction
import com.example.myfigma.bl.MainState
import com.example.myfigma.demo.cards
import com.example.myfigma.ui.theme.Secondary
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ShowHorizontalPager(state: MainState, dispatch: (MainAction) -> Unit) {
    val pagerState = rememberPagerState()
    HorizontalPager(
        count = cards.count(),
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 32.dp),
    ) { currentCard ->
        ShowCardConstraint(dispatch, cards[currentCard])
    }
    CardTitleEditDialog(card = cards[pagerState.currentPage], state = state,dispatch = dispatch)
    HorizontalPagerIndicator(
        pagerState = pagerState,
        activeColor = Secondary,
        modifier = Modifier
            .padding(16.dp),
    )
}
