package com.example.myfigma.ui

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.myfigma.R
import com.example.myfigma.bl.MainAction
import com.example.myfigma.bl.MainState
import com.example.myfigma.ui.theme.Background

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CardTitleEditDialog(
    card: CardDto,
    state: MainState,
    dispatch: (MainAction) -> Unit
) {
    if (state.showCardTitleEditDialog) {
        var inputText by remember {
            mutableStateOf(
                TextFieldValue(
                    card.title,
                    selection = TextRange(card.title.length)
                )
            )
        }
        val focusRequester = remember { FocusRequester() }
        Dialog(onDismissRequest = { dispatch(MainAction.OpenCardTitleEditDialog(false)) }) {
            Column(
                modifier = Modifier
                    .size(width = 312.dp, height = 184.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(color = Background)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.card_title),
                    style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Medium)
                )
                val keyboardController = LocalSoftwareKeyboardController.current
                val coroutineScope = rememberCoroutineScope()
                TextField(
                    value = inputText,
                    onValueChange = { textTitle -> inputText = textTitle },
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .onFocusChanged { state ->
                            if (state.hasFocus) {
                                coroutineScope.launch {
                                    delay(200)
                                    keyboardController?.show()
                                }
                            }
                        },
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.cancel_text),
                        style = TextStyle(fontSize = 15.sp),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable { dispatch(MainAction.OpenCardTitleEditDialog(false)) }
                    )
                    Text(
                        text = stringResource(R.string.ok_text),
                        style = TextStyle(fontSize = 15.sp),
                        modifier = Modifier
                            .clickable {
                                dispatch(MainAction.OpenCardTitleEditDialog(false));
                                dispatch(
                                    MainAction.CardTitleChange(
                                        title = inputText.text,
                                        idCard = card.id
                                    )
                                )
                            }
                    )
                }
            }
        }
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}


