package com.example.convertedwallet.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Money(

    val currency : String,
    val inCurrency : Int,
    val rateToUAH: Double,
    val rateToUSD: Double,
    val rateToEUR: Double,
    @PrimaryKey val id: Int? = null

)
