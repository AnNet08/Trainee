package com.example.myfigma.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfigma.R
import com.example.myfigma.ui.theme.Background
import com.example.myfigma.ui.theme.MyFigmaTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainPage() {
    MyFigmaTheme {
        Column(
            modifier = Modifier
                .background(Background)
                .fillMaxSize()
        ) {
            ScreenContent()
            ShowBottomNavigation()
        }
    }
}

@Composable
fun ScreenContent() {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyHeader()
        ShowHorizontalPager()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            ShowButton("Платежи", R.drawable.data_transfer)
            ShowButton("Выписка", R.drawable.check)
        }
    }
}


@Composable
fun ShowButton(title: String, img: Int) {
    Column(
        modifier = Modifier
            .size(88.dp)
            .clickable { /* Do something! */ },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(img),
            contentDescription = null
        )
        Text(
            text = title,
            style = TextStyle(fontSize = 13.sp),
            modifier = Modifier
                .padding(top = 6.dp)
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