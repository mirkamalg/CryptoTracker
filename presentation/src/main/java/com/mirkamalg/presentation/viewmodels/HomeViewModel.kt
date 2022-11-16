package com.mirkamalg.presentation.viewmodels

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

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

        fun getPrices(ids: String) {
            _loading.value = true
            launch(getPricesUseCase, ids) {
                onSuccess = {
                    _loading.value = false
                }
                onTerminate = {
                    _loading.value = false
                }
            }
        }

}