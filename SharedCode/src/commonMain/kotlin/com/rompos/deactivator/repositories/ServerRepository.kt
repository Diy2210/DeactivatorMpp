package com.rompos.deactivator.repositories

import com.rompos.deactivator.Server
import com.rompos.deactivator.Servers
import com.squareup.sqldelight.db.SqlDriver

//package com.rompos.deactivator.mpp.repositories
//
//expect fun createDB(): Server
//
//open class ServerRepository {
//    private val database = createDB()
//    private val serverQueries = database.serverQueries
//
//    // Insert New Server
//    fun insert(server: Servers) {
//        serverQueries.insertServer(server.title, server.url, server.token)
//    }
//
//    // Get All Servers
//    fun list(): List<Servers> {
//        return serverQueries.selectAll().executeAsList()
//    }
//
//    // Get Server by ID
////    fun get(id: Long): Query<Servers>? {
////        return serverQueries.selectServer(id)
////    }
//
//    fun get(id: Long): Servers? {
//        return serverQueries.selectServer(id).executeAsOne()
//    }
//
//    // Update Server
//    fun update(id: Long, title: String, url: String, token: String) {
//        serverQueries.updateServer(title, url, token, id)
//    }
//
//    // Delete Server
//    fun delete(id: Long) {
//        serverQueries.deleteServer(id)
//    }
//}

class ServersRepository(slqDriver: SqlDriver) {

    private val database = Server(slqDriver)
    private val serverQueries = database.serverQueries

    fun getAll(): List<Servers> {
        return serverQueries.selectAll().executeAsList()
    }

    fun get(id: Long): Servers {
        return serverQueries.selectServer(id).executeAsOne()
    }

    fun save(id: Long, model: Servers) {
        if (id > 0) {
            serverQueries.updateServer(
                model.title,
                model.url,
                model.token,
                id
            )
        } else {
            serverQueries.insertServer(
                model.title,
                model.url,
                model.token
            )
        }
    }

    fun delete(id: Long) {
        serverQueries.deleteServer(id)
    }
}