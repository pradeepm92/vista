package com.health.vistacan.utils

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.*

object TimerUtils {
    private var timerJob: Job? = null

    fun startTimer(duration: Int, interval: Long, onTick: (Long) -> Unit) {
        timerJob = GlobalScope.launch(Dispatchers.Main) {
            val handler = Handler(Looper.getMainLooper())
            var remainingTime = duration.toDouble()
            var runningTime = 0.0
            while (remainingTime >= 0) {
                onTick(runningTime.toLong())
                delay(interval)
                runningTime += interval
                remainingTime -= interval

            }
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
    }
}