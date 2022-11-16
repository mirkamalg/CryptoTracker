package com.mirkamalg.cryptotracker.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mirkamalg.domain.models.PriceEntity
import com.mirkamalg.domain.repositories.PricesRepository
import com.mirkamalg.domain.repositories.RecordsRepository
import com.mirkamalg.domain.repositories.SettingsRepository
import com.mirkamalg.presentation.viewmodels.HomeViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

/**
 * Created by Mirkamal on 17 Noyabr 2022
 */

@HiltWorker
class BackgroundWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val pricesRepository: PricesRepository,
    private val recordsRepository: RecordsRepository,
    private val settingsRepository: SettingsRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork() = try {
        val list = pricesRepository.getPrices(HomeViewModel.CRYPTOS, HomeViewModel.CURRENCY_DOLLARS)
        Timber.tag("WorkManager").e("Successful! $list")
        handleResult(list)
        Result.success()
    } catch (e: Exception) {
        Timber.tag("WorkManager").e("Failed! ${e.message}")
        Result.retry()
    }

    private fun handleResult(list: List<PriceEntity>) {
        list.forEach {
            val (min, max) = settingsRepository.loadSettings(it.id)
            if ((min == 0.0 && max == 0.0).not()) {
                if (it.price != null && it.price!! > max) {
                    //Higher than set amount
                    recordsRepository.saveNewRecord(it.id, it.price!!)
                    sendNotification(NotificationParams(TYPE.HIGH, max, it.price!!))
                } else if (it.price != null && it.price!! < min) {
                    //Lower than set amount
                    recordsRepository.saveNewRecord(it.id, it.price!!)
                    sendNotification(NotificationParams(TYPE.LOW, min, it.price!!))
                }
            }
        }
    }

    private fun sendNotification(params: NotificationParams) {
        //TODO send notification
    }

    private data class NotificationParams(val type: TYPE, val set: Double, val actual: Double)

    private enum class TYPE {
        HIGH, LOW
    }

}