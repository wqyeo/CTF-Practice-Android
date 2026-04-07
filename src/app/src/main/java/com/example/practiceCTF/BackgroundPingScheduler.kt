package com.example.practiceCTF

import android.util.Log
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

object BackgroundPingScheduler {
    private const val TAG = "BackgroundPing"
    private const val TARGET_URL = "https://httpin.org"

    private val scheduler = Executors.newSingleThreadScheduledExecutor()
    private val started = AtomicBoolean(false)

    fun start() {
        if (!started.compareAndSet(false, true)) {
            return
        }
        scheduler.scheduleWithFixedDelay(
            { ping() },
            0,
            15,
            TimeUnit.SECONDS
        )
    }

    private fun ping() {
        var connection: HttpURLConnection? = null
        try {
            connection = (URL(TARGET_URL).openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                connectTimeout = 3000
                readTimeout = 3000
                instanceFollowRedirects = true
            }
            connection.responseCode
        } catch (exception: Exception) {
            Log.d(TAG, "GET failed", exception)
        } finally {
            connection?.disconnect()
        }
    }
}

