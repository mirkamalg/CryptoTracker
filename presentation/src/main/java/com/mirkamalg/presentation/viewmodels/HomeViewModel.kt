package com.mirkamalg.presentation.viewmodels

import com.mirkamalg.domain.models.PriceEntity
import com.mirkamalg.domain.usecase.GetPricesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Created by Mirkamal on 16 November 2022
 */

@HiltViewModel
class HomeViewModel @Inject constructor(private val getPricesUseCase: GetPricesUseCase) :
    BaseViewModel() {

    companion object {
        const val CURRENCY_DOLLARS = "usd"
        const val CRYPTOS = "bitcoin,ethereum,ripple"
    }

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _coins = MutableStateFlow<List<PriceEntity>>(emptyList())
    val coins: StateFlow<List<PriceEntity>> = _coins

    fun getPrices(ids: String = CRYPTOS, currency: String = CURRENCY_DOLLARS) {
        launch(getPricesUseCase, GetPricesUseCase.GetPricesParams(ids, currency)) {
            onStart = {
                _loading.value = true
            }
            onSuccess = {
                _coins.value = it
            }
            onTerminate = {
                _loading.value = false
            }
        }
    }

}