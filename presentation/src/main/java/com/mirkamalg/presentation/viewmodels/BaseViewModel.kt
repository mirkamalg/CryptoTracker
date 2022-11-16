package com.mirkamalg.presentation.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirkamalg.domain.usecase.BaseUseCase
import com.mirkamalg.domain.usecase.RequestLifecycleBlock
import kotlinx.coroutines.launch

/**
 * Created by Mirkamal on 16 November 2022
 */

abstract class BaseViewModel : ViewModel() {

    protected fun <I, O, U : BaseUseCase<I, O>> launch(
        useCase: U,
        input: I,
        block: RequestLifecycleBlock<O> = {}
    ) {
        viewModelScope.launch {
            val request = BaseUseCase.Request<O>().apply(block)
            val requestLifecycleBlock: RequestLifecycleBlock<O> = {
                onStart = {
                    request.onStart?.invoke()
                }
                onSuccess = {
                    request.onSuccess(it)
                }
                onCancel = {
                    request.onCancel?.invoke(it)
                }
                onTerminate = {
                    request.onTerminate?.invoke()
                }
                onError = {
                    request.onError?.invoke(it) ?: handleError(it)
                }
            }
            useCase.execute(input, requestLifecycleBlock)
        }
    }

    private fun handleError(t: Throwable) {

    }

}