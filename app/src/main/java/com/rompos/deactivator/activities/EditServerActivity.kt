package com.rompos.deactivator.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.rompos.deactivator.R
import com.rompos.deactivator.app
import com.rompos.deactivator.databinding.ActivityEditServerBinding
import com.rompos.deactivator.helpers.Utils
import com.rompos.deactivator.models.ServerFormViewModel
import com.rompos.deactivator.repositories.ServersRepository
import kotlinx.android.synthetic.main.activity_edit_server.*
import kotlinx.coroutines.launch
import org.kodein.di.erased.instance

class EditServerActivity : AppCompatActivity() {

    private val repository: ServersRepository by app.kodein.instance()
    private var serverFormViewModel = ServerFormViewModel()
    private var serverId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityEditServerBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_server)

        serverId = intent.getLongExtra("ID", 0)
        if (serverId > 0) {
            supportActionBar?.title = getString(R.string.edit_server)
            lifecycleScope.launch {
                binding.progressBar.visibility = View.VISIBLE
                repository.get(serverId).let { server ->
                    serverFormViewModel.setForm(server)
                }
            }.also {
                binding.progressBar.visibility = View.GONE
            }
        }
        binding.item = serverFormViewModel

        saveBtn.setOnClickListener {
//            if (serverFormViewModel.isFormValid()) {
//                lifecycleScope.launch {
//                    saveRecord(serverFormViewModel)
//                }.also {
//                    setResult(Activity.RESULT_OK, Intent())
//                    finish()
//                }
//            } else {
//                Utils.snackMsg(editView, getString(R.string.error_empty_field))
//            }

//            val title = serverTitle.text.toString()
//            val url = serverUrl.text.toString()
//            val token = serverToken.text.toString()
//
//            val server = Servers(0, title, url, token)
//
//            if (title.isEmpty() || url.isEmpty() || token.isEmpty()) {
//                Utils.snackMsg(it, getString(R.string.error_empty_field))
//            } else {
//                lifecycleScope.launch {
////                    showProgress(true)
//                    try {
//                        repository.insert(server)
//                        println("//////////////" + repository.list())
//                    } catch (e: Exception) {
//                        Utils.snackMsg(it, e.message.toString())
//                    }
//                }.also {
//                    setResult(Activity.RESULT_OK, Intent())
//                    this.finish()
//                }
//            }

            if (serverFormViewModel.isFormValid()) {
                lifecycleScope.launch {
                    saveRecord(serverFormViewModel)
                }.also {
                    setResult(Activity.RESULT_OK, Intent())
                    finish()
                }
            } else {
                Utils.snackMsg(editView, getString(R.string.error_empty_field))
            }

        }

        cancelBtn.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun saveRecord(viewModel: ServerFormViewModel) {
        progressBar.visibility = View.VISIBLE
        try {
            repository.save(serverId, viewModel.getModel(serverId))
        } catch (e: Exception) {
            Utils.snackMsg(editView, e.message.toString())
        } finally {
            progressBar.visibility = View.GONE
        }
    }
}