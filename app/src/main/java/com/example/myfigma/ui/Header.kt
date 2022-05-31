package com.example.myfigma.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfigma.R
import com.example.myfigma.bl.MainAction

@Composable
fun MyHeader(value: Float, dispatch: (MainAction) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.header_button_menu),
            contentDescription = null,
            modifier = Modifier
                .clickable { dispatch(MainAction.OpenMenu) }
        )
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                alpha = 1 - value
            )
            AutoSizeText(
                text = "11 500 44444444444444444 500.00 UA",
                modifier = Modifier
                    .alpha(value),
                textStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Medium),
                textAlign = TextAlign.Center
            )
        }
        Image(
            painter = painterResource(R.drawable.header_button_chat),
            contentDescription = null,
            modifier = Modifier
                .clickable { dispatch(MainAction.OpenMessages) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyHeaderPreview() {
    MyHeader(1f) {
        //Preview
    }
}