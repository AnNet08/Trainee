package com.example.myfigma.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfigma.R
import com.example.myfigma.bl.MainAction
import com.example.myfigma.bl.MainSideEffect
import com.example.myfigma.bl.MainState
import com.example.myfigma.ui.theme.Background
import com.example.myfigma.ui.theme.ScrolledHeader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainPage(state: MainState, sideEffect: Flow<MainSideEffect>, dispatch: (MainAction) -> Unit) {
    var transformationOffset by remember { mutableStateOf(0f) }
    val alpha = if (transformationOffset < 0.75f) 0f else (transformationOffset - 0.75f) * 4
    Column(
        modifier = Modifier
            .background(color = ScrolledHeader.copy(alpha = alpha))
            .fillMaxSize()
    ) {
        MyHeader(value = alpha, state = state, dispatch = dispatch)
        ScreenContent(
            state = state,
            sideEffect = sideEffect,
            dispatch = dispatch
        ) {
            transformationOffset = it
        }
        ShowBottomNavigation()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.ScreenContent(
    state: MainState,
    sideEffect: Flow<MainSideEffect>,
    dispatch: (MainAction) -> Unit,
    onTransformationOffsetChange: (Float) -> Unit
) {
    var showSearchTransactionsField by remember { mutableStateOf(false) }
    Box(modifier = Modifier.weight(1f)) {
        val lazyListState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        var scrolledY = 0f
        var previousOffset = 0
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState
        ) {
            item {
                Box(modifier = Modifier
                    .graphicsLayer {
                        scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                        translationY = scrolledY * 1f
                        previousOffset = lazyListState.firstVisibleItemScrollOffset
                        val alpha: Float
                        if (lazyListState.firstVisibleItemIndex == 0) {
                            val firstItemSize = lazyListState.layoutInfo.visibleItemsInfo[0].size
                            alpha = lazyListState.firstVisibleItemScrollOffset.toFloat() / firstItemSize.toFloat()
                            showSearchTransactionsField = false
                        } else {
                            alpha = 1f
                        }
                        onTransformationOffsetChange(alpha)
                    }) {
                    MainScreen(state, sideEffect, dispatch)
                }
            }
            stickyHeader {
                Searcher(
                    state,
                    dispatch,
                    showSearchTransactionsField
                ) { showSearchTransactionsField = it }
                TransactionsListDivider()
            }
            val transactions = state.transactions
            items(transactions.count()) { currentItem ->
                TransactionsListItem(
                    transactions[currentItem],
                    onItemClick = { })
                TransactionsListDivider()
            }
        }
        if (showSearchTransactionsField) {
            SideEffect {
                coroutineScope.launch {
                    lazyListState.scrollToItem(1, 0)
                    showSearchTransactionsField = true
                }
            }
        }
    }
}

@Composable
fun MainScreen(state: MainState, sideEffect: Flow<MainSideEffect>, dispatch: (MainAction) -> Unit) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShowHorizontalPager(state, sideEffect, dispatch)
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ShowButton(
                "Платежи",
                R.drawable.data_transfer
            ) { dispatch(MainAction.OpenTransfers("Платежи")) }
            ShowButton("Выписка", R.drawable.check) { dispatch(MainAction.OpenChecks("Выписки")) }
        }
    }
}

@Composable
fun ShowButton(title: String, img: Int, dispatch: (String) -> Unit) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable { dispatch(title) }
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(img),
            modifier = Modifier
                .padding(bottom = 6.dp),
            contentDescription = null
        )
        Text(
            text = title,
            style = TextStyle(fontSize = 13.sp)
        )
    }
}

@Composable
private fun ShowBottomNavigation() {
    BottomNavigation(
        backgroundColor = Background
    ) {
        BottomNavigationItem(
            icon = {
                Image(
                    painter = painterResource(R.drawable.home),
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.bottom_navigation_home))
            },
            selected = true,
            onClick = {}
        )
        BottomNavigationItem(
            icon = {
                Image(
                    painter = painterResource(R.drawable.credit),
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.bottom_navigation_credit))
            },
            selected = true,
            onClick = {}
        )
        BottomNavigationItem(
            icon = {
                Image(
                    painter = painterResource(R.drawable.deposit),
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.bottom_navigation_deposit))
            },
            selected = true,
            onClick = {}
        )
        BottomNavigationItem(
            icon = {
                Image(
                    painter = painterResource(R.drawable.settings),
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.bottom_navigation_settings))
            },
            selected = true,
            onClick = {}
        )
    }
}
