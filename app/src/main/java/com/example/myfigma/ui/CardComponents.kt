package com.example.myfigma.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.myfigma.R
import com.example.myfigma.bl.MainAction
import com.example.myfigma.ui.theme.CardBgFinish
import com.example.myfigma.ui.theme.CardBgStart

@Composable
fun ShowCardBox(cardDto: CardDto) {
    Box(
        modifier = Modifier
            .size(width = 312.dp, height = 184.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        CardBgStart,
                        CardBgFinish
                    )
                )
            )
            .padding(16.dp)
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = cardDto.title,
                softWrap = false,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .widthIn(0.dp, 224.dp),
                style = TextStyle(fontSize = 15.sp)
            )
            Image(
                painter = painterResource(R.drawable.edit),
                contentDescription = null
            )
        }
        Image(
            painter = painterResource(R.drawable.wallet),
            contentDescription = null,
            modifier = Modifier
                .align(alignment = Alignment.TopEnd)
        )
        Text(
            text = cardDto.account,
            softWrap = false,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(fontSize = 14.sp),
            modifier = Modifier
                .padding(vertical = 24.dp)
                .widthIn(0.dp, 280.dp),
        )
        Text(
            text = cardDto.defaultText,
            softWrap = false,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(fontSize = 10.sp),
            modifier = Modifier
                .padding(top = 104.dp, bottom = 32.dp)
                .widthIn(0.dp, 224.dp),
        )
        Image(
            painter = painterResource(R.drawable.star),
            contentDescription = null,
            modifier = Modifier
                .align(alignment = Alignment.BottomStart)
        )
        AutoSizeText(
            text = "" + cardDto.balanceSum.toString() + " " + cardDto.currency,
            textStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Medium),
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .padding(start = 32.dp)
        )
    }
}

@Composable
fun ShowCardColumn(cardDto: CardDto) {
    Column(
        modifier = Modifier
            .size(width = 312.dp, height = 184.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        CardBgStart,
                        CardBgFinish
                    )
                )
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .widthIn(0.dp, 248.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = cardDto.title,
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .widthIn(0.dp, 224.dp),
                        style = TextStyle(fontSize = 15.sp)
                    )
                    Image(
                        painter = painterResource(R.drawable.edit),
                        contentDescription = null
                    )
                }
                Image(
                    painter = painterResource(R.drawable.wallet),
                    contentDescription = null
                )
            }
            Text(
                text = cardDto.account,
                softWrap = false,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier
                    .widthIn(0.dp, 280.dp)
            )
        }
        Column(verticalArrangement = Arrangement.Bottom) {
            Text(
                text = cardDto.defaultText,
                softWrap = false,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(fontSize = 10.sp),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .widthIn(0.dp, 280.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.star),
                    contentDescription = null
                )
                AutoSizeText(
                    text = "" + cardDto.balanceSum.toString() + " " + cardDto.currency,
                    textStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Medium)
                )
            }
        }
    }
}

@Composable
fun ShowCardConstraint(dispatch: (MainAction) -> Unit, card: CardDto) {
    val favouriteImg = if (card.favourite) R.drawable.star else R.drawable.not_selected_star
    ConstraintLayout(
        modifier = Modifier
            .size(width = 312.dp, height = 184.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        CardBgStart,
                        CardBgFinish
                    )
                )
            )
            .padding(16.dp)
    ) {
        val (titleText, editImg, walletImg, idText, defaultText, starImg, balanceText) = createRefs()
        Text(
            text = card.title,
            softWrap = false,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(end = 8.dp)
                .widthIn(0.dp, 224.dp)
                .constrainAs(titleText) {},
            style = TextStyle(fontSize = 15.sp)
        )
        Image(
            painter = painterResource(R.drawable.edit),
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    dispatch(MainAction.OpenCardTitleEditDialog(true))
                }
                .constrainAs(editImg) {
                    bottom.linkTo(titleText.bottom)
                    top.linkTo(titleText.top)
                    start.linkTo(titleText.end)
                }
        )
        Image(
            painter = painterResource(R.drawable.wallet),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(walletImg) {
                    end.linkTo(parent.end)
                }
        )
        Text(
            text = card.account,
            softWrap = false,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(fontSize = 14.sp),
            modifier = Modifier
                .widthIn(0.dp, 280.dp)
                .constrainAs(defaultText) {
                    top.linkTo(titleText.bottom)
                }
        )
        if (card.favourite) {
            Text(
                text = card.defaultText,
                softWrap = false,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(fontSize = 10.sp),
                modifier = Modifier
                    .widthIn(0.dp, 280.dp)
                    .constrainAs(idText) {
                        bottom.linkTo(starImg.top)
                    }
                    .padding(vertical = 8.dp)
            )
        }
        Image(
            painter = painterResource(favouriteImg),
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    //dispatch(MainAction.ChangeNeedToScrollToCardItem(false))
                    dispatch(MainAction.ChangeFavouriteCards(card.id))
                }
                .constrainAs(starImg) {
                    bottom.linkTo(parent.bottom)
                }
        )
        AutoSizeText(
            text = "" + String.format("%05.2f", card.balanceSum) + " " + card.currency,
            textStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Medium),
            textAlign = TextAlign.End,
            modifier = Modifier
                .padding(start = 32.dp)
                .constrainAs(balanceText) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(starImg.end)
                    end.linkTo(parent.end)
                })
    }
}