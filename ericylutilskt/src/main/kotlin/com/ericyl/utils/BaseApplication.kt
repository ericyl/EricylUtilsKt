package com.ericyl.utils

import android.app.Application
import kotlin.system.exitProcess

open class BaseApplication : Application() {

    open fun exitApp(exCode: Int) {
        android.os.Process.killProcess(android.os.Process.myPid())
        exitProcess(exCode)
    }

}
