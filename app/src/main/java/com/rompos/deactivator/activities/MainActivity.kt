package com.rompos.deactivator.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.rompos.deactivator.R
import com.rompos.deactivator.Servers
import com.rompos.deactivator.mpp.ServerRepository
import com.rompos.deactivator.adapters.ServersAdapter
import com.rompos.deactivator.helpers.Utils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        const val EDITED = 101
        const val DETAILS = 102
        const val CONNECTION_ERROR = 2
    }

    lateinit var repository: ServerRepository
    lateinit var adapter: ServersAdapter
    lateinit var serversList: List<Servers>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            val intent = Intent(applicationContext, EditServerActivity::class.java)
            startActivityForResult(intent, EDITED)
        }

        lifecycleScope.launch {
            progressBar.visibility = View.VISIBLE
            serversList = repository.list()
        }.also {
            adapter = ServersAdapter(
                serversList,

                // Click Server Action
                object : ServersAdapter.ClickCallback {
                    override fun onItemClicked(item: Servers) {
                        val intent = Intent(applicationContext, PluginsActivity::class.java)
                        with(item) {
                            intent.putExtra("title", title)
                            intent.putExtra("url", url)
                            intent.putExtra("token", token)
                        }
                        startActivityForResult(intent, DETAILS)
                    }
                },

                // Edit Server Action
                object : ServersAdapter.EditClickCallback {
                    override fun onEditItemClicked(item: Servers) {
                        val intent = Intent(applicationContext, EditServerActivity::class.java)
                        intent.putExtra("ID", item.id)
                        startActivityForResult(intent, EDITED)
                    }
                },

                // Delete Server Action
                object : ServersAdapter.DeleteClickCallback {
                    override fun onDeleteItemClicked(item: Servers) {
                        AlertDialog.Builder(this@MainActivity)
                            .setTitle(getString(R.string.confirm_delete, item.title))
                            .setCancelable(true)
                            .setPositiveButton(android.R.string.yes) { _, _ ->
                                try {
                                    repository.delete(item.id.toLong()).also {
                                        adapter.items = repository.list()
                                        adapter.notifyDataSetChanged()
                                        Utils.snackMsg(mainView, getString(R.string.deleted))
                                    }
                                } catch (e: Exception) {
                                    Utils.snackMsg(mainView, e.message.toString())
                                }
                            }
                            .setNegativeButton(android.R.string.no) { _, _ ->
                                // nothing to do
                            }
                            .show()
                    }
                }
            )
            servers.adapter = adapter
            progressBar.visibility = View.GONE
        }

        swipeContainer.setOnRefreshListener {
            adapter.items = repository.list()
            adapter.notifyDataSetChanged()
            if (swipeContainer.isRefreshing) {
                swipeContainer.isRefreshing = false
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDITED && resultCode == Activity.RESULT_OK) {
            adapter.items = repository.list()
            adapter.notifyDataSetChanged()
        } else if (requestCode == DETAILS && resultCode == CONNECTION_ERROR) {
            val message = data!!.getStringExtra("message")
            Utils.snackMsg(mainView, message!!)
            println(message)
        }
    }
}
