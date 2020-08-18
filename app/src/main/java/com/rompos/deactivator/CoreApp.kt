package com.rompos.deactivator

import com.rompos.deactivator.repositories.ServersRepository
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.HttpClient
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.singleton

class CoreApp(sqlDriver: SqlDriver) {
    var kodein = Kodein {
        bind() from singleton {
            ServersRepository(
                sqlDriver
            )
        }
        bind() from singleton { HttpClient() }
    }
}

var isInitialized = false
    private set
lateinit var app: CoreApp
    private set

fun initApplication(sqlDriver: SqlDriver) {
    if (!isInitialized) {
        app = CoreApp(sqlDriver)
        isInitialized = true
    }
}
