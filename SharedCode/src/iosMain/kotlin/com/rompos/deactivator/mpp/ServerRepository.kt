package com.rompos.deactivator.mpp

import com.rompos.deactivator.Server
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual fun createDB(): Server {
    val driver = NativeSqliteDriver(Server.Schema,"Server.db")
    return Server(driver)
}
