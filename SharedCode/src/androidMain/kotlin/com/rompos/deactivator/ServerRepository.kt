package com.rompos.deactivator

import com.squareup.sqldelight.android.AndroidSqliteDriver
import android.content.Context

lateinit var appContext: Context

actual fun createDB() : Server {
    val driver = AndroidSqliteDriver(Server.Schema, appContext, "Server.db")
    return Server(driver)
}