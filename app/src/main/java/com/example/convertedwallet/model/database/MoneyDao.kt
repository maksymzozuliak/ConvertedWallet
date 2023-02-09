package com.example.convertedwallet.model.database

import androidx.room.*
import com.example.convertedwallet.model.Money
import kotlinx.coroutines.flow.Flow

@Dao
interface MoneyDao {

    @Query("SELECT * FROM money")
    fun getMoneyList(): Flow<List<Money>>

    @Query("SELECT * FROM money WHERE id = :id")
    suspend fun getMoneyById(id: Int): Money?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoney(money: Money)

    @Delete
    suspend fun deleteMoney(money: Money)
}