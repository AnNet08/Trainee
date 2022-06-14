package com.example.myfigma.demo

import com.example.myfigma.R
import com.example.myfigma.ui.CardDto
import com.example.myfigma.ui.TransactionItemDto
import java.math.BigDecimal

var cardsDemo = mutableListOf(
    CardDto(
        id = "001",
        title = "Account name",
        account = "UA 000000000000000",
        defaultText = "По умолчанию",
        balanceSum = 11500.50,
        currency = "UAH",
        favourite = false,
    ),
    CardDto(
        id = "002",
        title = "Title",
        account = "UA 000000000000000",
        defaultText = "По умолчанию",
        balanceSum = 200000.50,
        currency = "UAH",
        favourite = false,
    ),
    CardDto(
        id = "003",
        title = "Title LongLongTitle Very Long Title LongLongTitle Very Long Title",
        account = "UA 000000000000000",
        defaultText = "По умолчанию",
        balanceSum = 3000000.50,
        currency = "UAH",
        favourite = false,
    ),
    CardDto(
        id = "004",
        title = "fours card",
        account = "UA 000000000000000",
        defaultText = "По умолчанию",
        balanceSum = 0.00,
        currency = "UAH",
        favourite = false,
    )
)

val sectionTransactions = listOf(
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
