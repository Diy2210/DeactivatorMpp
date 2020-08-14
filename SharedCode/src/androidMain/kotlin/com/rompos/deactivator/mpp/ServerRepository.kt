package com.rompos.deactivator.mpp

import com.squareup.sqldelight.android.AndroidSqliteDriver
import android.content.Context
import com.rompos.deactivator.Server

lateinit var appContext: Context

actual fun createDB() : Server {
    val driver = AndroidSqliteDriver(Server.Schema, appContext, "Server.db")
    return Server(driver)
}