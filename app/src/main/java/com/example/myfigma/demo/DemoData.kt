package com.example.myfigma.demo

import com.example.myfigma.R
import com.example.myfigma.ui.Card
import com.example.myfigma.ui.TransactionHeaderDto
import com.example.myfigma.ui.TransactionItemDto

val cards = listOf(
    Card(
        "Account name",
        "UA 000000000000000",
        "По умолчанию",
        "11 500.00 UA"
    ),
    Card(
        "Title",
        "UA 000000000000000",
        "По умолчанию",
        "11.00 UA"
    ),
    Card(
        "Title LongLongTitle Very Long Title LongLongTitle Very Long Title",
        "UA 000000000000000",
        "По умолчанию",
        "11 500 44444444444444444 500.00 UA"
    ),
    Card(
        "fours card",
        "UA 000000000000000",
        "По умолчанию",
        "0.00 UA"
    )
)

val sectionTransactions = listOf(
    TransactionHeaderDto(
        title = "Сегодня"
    ),
    TransactionItemDto(
        icon = R.drawable.transfer,
        title = "Между своими счетами",
        iban = "UA 85 399622 0000026205500011673",
        attention = "",
        sum = "-100.00 UAH"
    ),
    TransactionItemDto(
        icon = R.drawable.swift,
        title = "SWIFT - платёж",
        iban = "UA 85 399622 0000026205500011673",
        attention = "",
        sum = "-100.00 UAH"
    ),
    TransactionItemDto(
        icon = R.drawable.uk_territory,
        title = "Платежи по Украине",
        iban = "UA 85 399622 0000026205500011673",
        attention = "",
        sum = "-57 870.00 UAH"
    ),
    TransactionItemDto(
        icon = R.drawable.transfer_error,
        title = "Между своими счетами",
        iban = "UA 85 399622 0000026205500011673",
        attention = "Ошибка перевода",
        sum = "-57 870.00 UAH"
    ),
    TransactionHeaderDto(
        title = "Вчера"
    ),
    TransactionItemDto(
        icon = R.drawable.swift,
        title = "SWIFT - платёж",
        iban = "UA 85 399622 0000026205500011673",
        attention = "",
        sum = "-100.00 UAH"
    ),
    TransactionItemDto(
        icon = R.drawable.uk_territory,
        title = "Платежи по Украине",
        iban = "UA 85 399622 0000026205500011673",
        attention = "",
        sum = "-57 870.00 UAH"
    ),
    TransactionItemDto(
        icon = R.drawable.swift,
        title = "SWIFT - платёж",
        iban = "UA 85 399622 0000026205500011673",
        attention = "",
        sum = "-100.00 UAH"
    ),
    TransactionHeaderDto(
        title = "19.05.2021"
    ),
    TransactionItemDto(
        icon = R.drawable.transfer_error,
        title = "Между своими счетами",
        iban = "UA 85 399622 0000026205500011673",
        attention = "Ошибка перевода",
        sum = "-57 870.00 UAH"
    ),
    TransactionItemDto(
        icon = R.drawable.swift_error,
        title = "SWIFT - платёж",
        iban = "UA 85 399622 0000026205500011673",
        attention = "Ошибка перевода",
        sum = "-100.00 UAH"
    ),
    TransactionItemDto(
        icon = R.drawable.transfer_error,
        title = "Между своими счетами",
        iban = "UA 85 399622 0000026205500011673",
        attention = "Ошибка перевода",
        sum = "-57 870.00 UAH"
    ),
    TransactionItemDto(
        icon = R.drawable.uk_territory,
        title = "Платежи по Украине",
        iban = "UA 85 399622 0000026205500011673",
        attention = "",
        sum = "-57 870.00 UAH"
    )
)
