package com.example.convertedwallet.model.internet

data class CurrencyResponse(
    val base: String,
    val date: String,
    val rates: Rates,
    val success: Boolean,
    val timestamp: Int
)

data class Rates(
    val EUR : Double,
    val UAH : Double,
    val USD : Double
)