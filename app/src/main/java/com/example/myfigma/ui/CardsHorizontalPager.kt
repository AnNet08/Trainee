package com.example.myfigma.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ShowHorizontalPager(){

    val pagerState = rememberPagerState()
    HorizontalPager(
        count = 5,
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) { page ->
        ShowCardConstraint(
            Card(
                "Title",
                "UA 000000000000000",
                "По умолчанию",
                "11 500 500.00 UA"
            ))
    }
    HorizontalPagerIndicator(
        pagerState = pagerState,
        modifier = Modifier
            .padding(16.dp),
    )

}