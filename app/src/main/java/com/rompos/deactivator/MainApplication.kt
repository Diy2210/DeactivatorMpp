package com.rompos.deactivator

import android.app.Application
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.rompos.deactivator.Server

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initApplication(AndroidSqliteDriver(Server.Schema, applicationContext, "Server.db"))
    }
}
