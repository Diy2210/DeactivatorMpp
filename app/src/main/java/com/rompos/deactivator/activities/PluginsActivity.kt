package com.rompos.deactivator.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.rompos.deactivator.R
import com.rompos.deactivator.Servers
import com.rompos.deactivator.adapters.PluginsAdapter
import com.rompos.deactivator.helpers.Utils
import com.rompos.deactivator.models.PluginViewModel
import com.rompos.deactivator.models.PluginsResponseModel
import com.rompos.deactivator.api.DeactivatorApi
import com.rompos.deactivator.app
import com.rompos.deactivator.repositories.ServersRepository
import io.ktor.client.features.ClientRequestException
import kotlinx.android.synthetic.main.activity_plugins.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse
import org.kodein.di.erased.instance

class PluginsActivity : AppCompatActivity() {

    private val repository: ServersRepository by app.kodein.instance()
    private lateinit var currentServer: Servers
    lateinit var token: String
    private var serverId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plugins)

        plugins.visibility = View.INVISIBLE

        serverId = intent.getLongExtra("ID", 0)

        lifecycleScope.launch {
            repository.get(serverId).let { server ->
                currentServer = server
                supportActionBar?.title = server?.title
            }
        }.also {
            getPlugins()
        }

        swipeContainer.setOnRefreshListener {
            getPlugins()
            if (swipeContainer.isRefreshing) {
                swipeContainer.isRefreshing = false
            }
        }

    }

    private fun generatePage(response: String) {
        val resp = Json.decodeFromString(PluginsResponseModel.serializer(), response)
        if (resp.success) {
            plugins.adapter = PluginsAdapter(this, resp.data)
            plugins.visibility = View.VISIBLE
        } else {
            Utils.snackMsg(pluginsView, getString(R.string.server_error))
        }
    }

    private fun getPlugins() {
        lifecycleScope.launch {
            progressBar.visibility = View.VISIBLE
            try {
//                DeactivatorApi.also { response ->
//                    generatePage(response)
//                }
            } catch (e: ClientRequestException) {
                intent.putExtra(
                    "message",
                    e.response.status.value.toString() + " " + e.response.status.description
                )
                setResult(MainActivity.CONNECTION_ERROR, intent)
                finish()
            } catch (e: Exception) {
                intent.putExtra("message", e.message.toString())
                setResult(MainActivity.CONNECTION_ERROR, intent)
                finish()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }

    fun sendStateRequest(pluginViewModel: PluginViewModel, state: Boolean) {
        lifecycleScope.launch {
            progressBar.visibility = View.VISIBLE
            try {
//                DeactivatorApi(currentServer).updateStatus(pluginViewModel, state).also { _ ->
//                    getPlugins()
//                }
            } catch (e: Exception) {
                Utils.snackMsg(pluginsView, e.message.toString())
            }
        }
    }
}