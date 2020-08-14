package com.rompos.deactivator.mpp

import com.rompos.deactivator.Server
import com.rompos.deactivator.Servers

expect fun createDB(): Server

open class ServerRepository {
    private val database = createDB()
    private val serverQueries = database.serverQueries

    // Insert New Server
    fun insert(server: Servers) {
        serverQueries.insertServer(server.title, server.url, server.token)
    }

    // Get All Servers
    fun list(): List<Servers> {
        return serverQueries.selectAll().executeAsList()
    }

    // Get Server by ID
//    fun get(id: Long): Query<Servers>? {
//        return serverQueries.selectServer(id)
//    }

    fun get(id: Long): Servers? {
        return serverQueries.selectServer(id).executeAsOne()
    }

    // Update Server
    fun update(id: Long, title: String, url: String, token: String) {
        serverQueries.updateServer(title, url, token, id)
    }

    // Delete Server
    fun delete(id: Long) {
        serverQueries.deleteServer(id)
    }
}