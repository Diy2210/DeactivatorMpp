package com.rompos.deactivator.models

import androidx.lifecycle.ViewModel
import com.rompos.deactivator.Servers

data class ServerViewModel(
    var title: String? = null,
    var url: String? = null,
    var token: String? = null
): ViewModel() {

    fun setModel(server: Servers) : ServerViewModel {
        title = server.title
        url = server.url
        token = server.token

        return this
    }
}