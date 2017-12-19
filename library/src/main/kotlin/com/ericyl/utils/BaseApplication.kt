package com.ericyl.utils

import android.app.Application

open class BaseApplication : Application() {

    open fun exitApp(exCode: Int) {
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(exCode)
    }

}
