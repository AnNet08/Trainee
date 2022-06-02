package com.example.myfigma.ui

data class CardDto(
    val id: String,
    var title: String,
    val account: String,
    val defaultText: String,
    val balanceSum: Double,
    val currency: String,
    var favourite: Int
)

data class TransactionItemDto(
    val icon: Int = 0,
    val title: String = "",
    val iban: String = "",
    val attention: String = "",
    val sum: String = ""
)
