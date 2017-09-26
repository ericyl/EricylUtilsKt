package com.ericyl.utils.util

import android.os.Handler
import android.os.Message
import android.os.SystemClock

/**
 * Created by ericyl on 2017/9/21.
 */
private const val COUNT_UP_TIMER_MSG = 0x1024

abstract class CountUpTimer(countUpInterval: Long) {
    private var mStopTimeInFuture: Long = 0
    private var mPauseTimeInFuture: Long = 0
    private var isStop = false
    private var isPause = false

    @Synchronized private fun start(millisInFuture: Long): CountUpTimer? {
        isStop = false
        if (millisInFuture < 0)
            return null
        mStopTimeInFuture = SystemClock.elapsedRealtime() - millisInFuture
        mHandler.removeMessages(COUNT_UP_TIMER_MSG)
        mHandler.sendMessage(mHandler.obtainMessage(COUNT_UP_TIMER_MSG))
        return this
    }

    @Synchronized
    fun start() {
        start(0)
    }

    @Synchronized
    fun stop() {
        isStop = true
        mHandler.removeMessages(COUNT_UP_TIMER_MSG)
    }

    @Synchronized
    fun pause() {
        if (isStop) return

        isPause = true
        mPauseTimeInFuture = SystemClock.elapsedRealtime() - mStopTimeInFuture
        mHandler.removeMessages(COUNT_UP_TIMER_MSG)
    }

    @Synchronized
    fun restart() {
        if (isStop || !isPause) return

        isPause = false
        start(mPauseTimeInFuture)
    }

    @Synchronized
    fun recycledStart() {
        isStop = false
        isPause = false
        mHandler.removeMessages(COUNT_UP_TIMER_MSG)
        mHandler.sendMessage(mHandler.obtainMessage(COUNT_UP_TIMER_MSG))
    }

    private val mHandler = object : Handler() {

        override fun handleMessage(msg: Message) {

            synchronized(this@CountUpTimer) {
                if (isStop || isPause)
                    return

                val millisLeft = SystemClock.elapsedRealtime() - mStopTimeInFuture
                if (millisLeft < countUpInterval) {
                    sendMessageDelayed(obtainMessage(COUNT_UP_TIMER_MSG), countUpInterval)
                } else {
                    onTick(millisLeft)
                    sendMessageDelayed(obtainMessage(COUNT_UP_TIMER_MSG), countUpInterval)
                }
            }
        }
    }

    abstract fun onTick(millisUntilFinished: Long)

}