package com.example.myfigma.ui

import java.math.BigDecimal

data class CardDto(
    var id: String,
    var title: String,
    val account: String,
    val defaultText: String,
    val balanceSum: Double,
    val currency: String
)

data class TransactionItemDto(
    val icon: Int = 0,
    val title: String = "",
    val iban: String = "",
    val attention: String = "",
    val sum: String = ""
)
