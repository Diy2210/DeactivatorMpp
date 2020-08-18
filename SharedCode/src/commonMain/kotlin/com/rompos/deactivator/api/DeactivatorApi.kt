package com.rompos.deactivator.api

import com.rompos.deactivator.Servers
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class DeactivatorApi(server: Servers) {

//    companion object {
        private val currentServer = server
    suspend fun getPlugins(host: String, token: String): String {
        val client = HttpClient()
        return client.get("${host}/wp-json/deactivator/v1/list?token=${token}") {
        }
    }

    suspend fun getPluginsList(): String {
        val client = HttpClient()
        return client.get("${currentServer.url}/wp-json/deactivator/v1/list?token=${currentServer.token}")
    }

//        suspend fun updateStatus(host: String, token: String, id: String, state: Boolean): String {
//            val client = HttpClient()
//            var status: String = "deactivate"
//            if (state) {
//                status = "activate"
//            }
//            val params = ContentDisposition.Parameters.build {
//                append("token", token)
//                append("id", id)
//                append("status", status)
//            }
//            return client.submitForm("${host}/wp-json/deactivator/v1/update", params)
//        }
//    }
}