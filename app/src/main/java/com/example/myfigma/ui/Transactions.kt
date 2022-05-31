package com.example.myfigma.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfigma.R
import com.example.myfigma.bl.MainAction
import com.example.myfigma.bl.MainState
import com.example.myfigma.ui.theme.*

@Composable
fun TransactionsListItem(transactionItem: TransactionItemDto, onItemClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(transactionItem.title) })
            .background(color = Surface)
            .padding(start = 16.dp, top = 16.dp, end = 14.dp, bottom = 11.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Image(
                painter = painterResource(id = transactionItem.icon),
                contentDescription = ""
            )
            Column(
                modifier = Modifier.padding(start = 17.dp)
            ) {
                Text(
                    text = transactionItem.title,
                    style = TextStyle(fontSize = 15.sp, color = OnSurface)
                )
                Text(
                    text = transactionItem.iban,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = OnSurfaceVariant
                    )
                )
                if (transactionItem.attention != "") {
                    Text(
                        text = transactionItem.attention,
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Attention
                        )
                    )
                }
            }
        }
        Text(
            text = transactionItem.sum,
            style = TextStyle(fontSize = 14.sp, color = OnSurface),
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun Searcher(state: MainState, dispatch: (MainAction) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(
                RoundedCornerShape(
                    topEnd = 14.dp,
                    topStart = 14.dp
                )
            )
            .background(color = Surface)
            .padding(horizontal = 16.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box {
            if (state.searchText.isEmpty()) {
                Text(
                    text = stringResource(R.string.bottom_sheet_list_top_header),
                    style = TextStyle(fontSize = 18.sp, color = SurfaceSelected)
                )
            }
            BasicTextField(
                value = state.searchText,
                onValueChange = { value ->
                    dispatch(MainAction.SearchTextChanged(value))
                })
        }
        Image(
            painter = painterResource(R.drawable.button_search),
            contentDescription = ""
        )
    }
}

@Composable
fun TransactionsListHeader(transactionHeader: TransactionHeaderDto) {
    Text(
        text = transactionHeader.title,
        style = TextStyle(fontSize = 18.sp, color = SurfaceSelected),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color = Surface)
            .padding(horizontal = 16.dp, vertical = 13.dp)
    )
}

@Composable
fun TransactionsListDivider() {
    Divider(
        color = Divider,
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    )
}
