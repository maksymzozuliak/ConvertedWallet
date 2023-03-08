package com.example.convertedwallet.ui.main_screen

import android.content.SharedPreferences
import android.icu.util.Currency.CurrencyUsage
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.input.key.Key.Companion.D
import androidx.compose.ui.input.key.Key.Companion.E
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convertedwallet.model.BaseCurrency
import com.example.convertedwallet.model.Money
import com.example.convertedwallet.model.internet.CurrencyResponse
import com.example.convertedwallet.model.internet.Rates
import com.example.convertedwallet.model.internet.Resource
import com.example.convertedwallet.model.repository.InternetRepository
import com.example.convertedwallet.model.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val internetRepository: InternetRepository,
    private val useCases: UseCases
): ViewModel() {

    init {
        getMoney()
    }

    private val _showSnackbar = mutableStateOf("")
    val showSnackbar: State<String> = _showSnackbar

    private var getMoneyJob: Job? = null

    private val _moneyList = mutableStateOf(listOf<Money>())
    val moneyList: State<List<Money>> = _moneyList

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _currentCurrency = mutableStateOf(BaseCurrency.UAH)
    val currentCurrency: State<BaseCurrency> = _currentCurrency

    sealed class MoneyEvent {

        object UpdateRates : MoneyEvent()
        class DeleteMoney(val money: Money): MoneyEvent()
        class ChangeCurrency(val currency: BaseCurrency): MoneyEvent()
        class SaveMoney(val currency: String, val inCurrency: Int, val money: Money?): MoneyEvent()

    }

    fun closeSnackbar() {
        _showSnackbar.value = ""
    }

    fun onEvent(event: MoneyEvent) {
        when(event) {
            is MoneyEvent.UpdateRates ->{

                if (!_isLoading.value) {
                    updateRates()
                } else {
                    _showSnackbar.value = "Loading"
                }
            }

            is MoneyEvent.DeleteMoney ->{
                viewModelScope.launch {
                    useCases.deleteMoney(event.money)
                    _showSnackbar.value = "Deleted"
                }
            }
            is MoneyEvent.ChangeCurrency ->{
                _currentCurrency.value = event.currency
            }
            is MoneyEvent.SaveMoney -> {
                viewModelScope.launch {

                    val rates = getCurrencyRates(event.currency)

                    if (rates == null && event.money != null) {
                        useCases.addMoney(event.money.copy(
                            currency = event.currency,
                            inCurrency = event.inCurrency,
                            )
                        )
                    } else if (rates != null && event.money != null) {
                        useCases.addMoney(event.money.copy(
                            currency = event.currency,
                            inCurrency = event.inCurrency,
                            rateToUAH = rates.UAH,
                            rateToEUR = rates.EUR,
                            rateToUSD = rates.USD
                            )
                        )
                    }
                    else if (rates != null) {
                        useCases.addMoney(
                            Money(
                                currency = event.currency,
                                inCurrency = event.inCurrency,
                                rateToUAH = rates.UAH,
                                rateToEUR = rates.EUR,
                                rateToUSD = rates.USD
                            )
                        )
                    }
                }
            }
        }
    }

    private fun updateRates() {

        _isLoading.value = true

        viewModelScope.launch {

            _moneyList.value.forEach { money ->

                updateSingleRate(money)
            }

            _isLoading.value = false
        }
    }

    private suspend fun updateSingleRate(money: Money) {

        val response = internetRepository.getRates(money.currency)

        if (response is Resource.Success) {
            useCases.addMoney(money.copy(
                rateToUAH = response.data!!.rates.UAH,
                rateToUSD = response.data.rates.USD,
                rateToEUR = response.data.rates.EUR
            ))
        }
    }

    private suspend fun getCurrencyRates(currency : String) : Rates? {

        val response = internetRepository.getRates(currency)

        if (response is Resource.Success) {

            return response.data!!.rates

        } else {

            _showSnackbar.value = response.message!!
            return null

        }
    }

    private fun getMoney() {
        getMoneyJob?.cancel()
        getMoneyJob = useCases.getMoneyList()
            .onEach { money ->
                _moneyList.value = money
            }
            .launchIn(viewModelScope)
    }
}