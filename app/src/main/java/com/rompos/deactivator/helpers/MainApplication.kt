package com.rompos.deactivator.helpers

import android.app.Application
import com.rompos.deactivator.appContext

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        appContext = this
    }
}