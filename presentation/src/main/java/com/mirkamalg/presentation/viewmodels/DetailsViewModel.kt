package com.mirkamalg.presentation.viewmodels

import com.mirkamalg.domain.usecase.ReadCryptoRecordUseCase
import com.mirkamalg.domain.usecase.ReadSettingForCryptoUseCase
import com.mirkamalg.domain.usecase.SaveCryptoSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Created by Mirkamal on 16 Noyabr 2022
 */

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val readSettingForCryptoUseCase: ReadSettingForCryptoUseCase,
    private val saveCryptoSettingsUseCase: SaveCryptoSettingsUseCase,
    private val readCryptoRecordUseCase: ReadCryptoRecordUseCase
) : BaseViewModel() {

    private val _minMax = MutableStateFlow(Pair(0.0, 0.0))
    val minMax: StateFlow<Pair<Double, Double>> = _minMax

    private val _records = MutableStateFlow<List<Double>>(emptyList())
    val records: StateFlow<List<Double>> = _records

    fun loadSettings(`for`: String) {
        launch(readSettingForCryptoUseCase, `for`) {
            onSuccess = {
                _minMax.value = it
            }
        }
    }

    fun saveSettings(`for`: String, min: String, max: String) {
        launch(
            saveCryptoSettingsUseCase,
            SaveCryptoSettingsUseCase.SaveCryptoSettingsParams(`for`, min, max)
        ) {
            onTerminate = {
                loadSettings(`for`)
            }
        }
    }

    fun loadRecords(`for`: String) {
        launch(readCryptoRecordUseCase, `for`) {
            onSuccess = {
                _records.value = it
            }
        }
    }
}