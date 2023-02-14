package com.example.convertedwallet.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.convertedwallet.model.database.MoneyDatabase
import com.example.convertedwallet.model.internet.CurrencyApi
import com.example.convertedwallet.model.repository.InternetRepository
import com.example.convertedwallet.model.repository.InternetRepositoryImpl
import com.example.convertedwallet.model.repository.MoneyRepository
import com.example.convertedwallet.model.repository.MoneyRepositoryImpl
import com.example.convertedwallet.model.spName
import com.example.convertedwallet.model.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://api.exchangeratesapi.io/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Database

    @Provides
    @Singleton
    fun provideMoneyDatabase(app: Application): MoneyDatabase{
        return Room.databaseBuilder(
            app,
            MoneyDatabase::class.java,
            MoneyDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMoneyRepository(db : MoneyDatabase): MoneyRepository {
        return MoneyRepositoryImpl(db.moneyDao)
    }

    @Provides
    @Singleton
    fun provideMoneyUseCases(repository: MoneyRepository): UseCases {
        return UseCases(
            getMoney = GetMoney(repository),
            deleteMoney = DeleteMoney(repository),
            addMoney = AddMoney(repository),
            getMoneyList = GetMoneyList(repository)
        )
    }

    // Api

    @Singleton
    @Provides
    fun provideCurrencyApi(): CurrencyApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CurrencyApi::class.java)

    @Singleton
    @Provides
    fun provideMainRepository(api: CurrencyApi): InternetRepository = InternetRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideSharedPref(app: Application): SharedPreferences {
        return app.applicationContext.getSharedPreferences(
            spName,
            Context.MODE_PRIVATE
        )
    }

}