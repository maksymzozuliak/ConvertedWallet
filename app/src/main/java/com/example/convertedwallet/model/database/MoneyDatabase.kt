package com.example.convertedwallet.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.convertedwallet.model.Money

@Database(
    entities = [Money::class],
    version = 1
)

abstract class MoneyDatabase: RoomDatabase() {

    abstract val moneyDao: MoneyDao

    companion object {
        const val DATABASE_NAME = "money_db"
    }
}