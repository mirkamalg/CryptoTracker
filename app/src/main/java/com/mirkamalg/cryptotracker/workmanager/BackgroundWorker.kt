package com.mirkamalg.cryptotracker.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mirkamalg.cryptotracker.R
import com.mirkamalg.domain.models.PriceEntity
import com.mirkamalg.domain.repositories.PricesRepository
import com.mirkamalg.domain.repositories.RecordsRepository
import com.mirkamalg.domain.repositories.SettingsRepository
import com.mirkamalg.presentation.viewmodels.HomeViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlin.random.Random

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

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "crypto_price"
        private const val NOTIFICATION_CHANNEL_NAME = "Crypto prices"
        private const val NOTIFICATION_CHANNEL_DESCRIPTION = "Alerts for crypto prices"
    }

    override suspend fun doWork() = try {
        val list = pricesRepository.getPrices(HomeViewModel.CRYPTOS, HomeViewModel.CURRENCY_DOLLARS)
        Log.d("WorkManager", "Successful! $list")
        handleResult(list)
        Result.success()
    } catch (e: Exception) {
        Log.d("WorkManager", "Exception! ${e.message}")
        Result.retry()
    }

    private fun handleResult(list: List<PriceEntity>) {
        list.forEach {
            val values = settingsRepository.loadSettings(it.cryptoName)
            val (min, max) = values
            if (shouldCheck(values)) {
                if (it.price != null && it.price!! > max) {
                    //Higher than set amount
                    recordsRepository.saveNewRecord(it.id, it.price!!)
                    sendNotification(NotificationParams(TYPE.HIGH, it.cryptoName, max, it.price!!))
                } else if (it.price != null && it.price!! < min) {
                    //Lower than set amount
                    recordsRepository.saveNewRecord(it.id, it.price!!)
                    sendNotification(NotificationParams(TYPE.LOW, it.cryptoName, min, it.price!!))
                }
            }
        }
    }

    private fun shouldCheck(pair: Pair<Double, Double>) =
        (pair.first == 0.0 && pair.second == 0.0).not()

    private fun sendNotification(params: NotificationParams) {
        Log.d("WorkManager", "Sending notification..")
        createNotificationChannel()
        val builder = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(params.name)
            .setContentText(
                "The price of ${params.name} is ${params.actual}! This is ${
                    if (params.type == TYPE.HIGH) "higher" else "lower"
                } than the value you've set (${params.set})"
            )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(applicationContext)) {
            notify(Random.nextInt(), builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = NOTIFICATION_CHANNEL_NAME
            val descriptionText = NOTIFICATION_CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager = getSystemService(
                applicationContext,
                NotificationManager::class.java
            )
            notificationManager?.createNotificationChannel(channel)
        }
    }

    private data class NotificationParams(
        val type: TYPE,
        val name: String,
        val set: Double,
        val actual: Double
    )

    private enum class TYPE {
        HIGH, LOW
    }

}