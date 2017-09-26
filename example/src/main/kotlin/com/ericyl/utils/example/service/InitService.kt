package com.ericyl.utils.example.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.ericyl.utils.example.utils.createDataDatabase

/**
 * Created by ericyl on 2017/9/18.
 */
class InitService: Service() {

    private val initBinder = InitBinder()

    override fun onBind(p0: Intent?): IBinder {
        return initBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    inner class InitBinder: Binder() {
        fun getService() : InitService{
            return this@InitService
        }
    }

    fun initSystem(){
        this.createDataDatabase()


    }

    private fun initData(){
//        this.create(SingleOnSubscribe<Long> {
//
//        })
    }


}