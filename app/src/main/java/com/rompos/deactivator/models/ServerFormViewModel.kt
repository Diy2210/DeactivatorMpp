package com.rompos.deactivator.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.rompos.deactivator.BR
import com.rompos.deactivator.Server
import com.rompos.deactivator.Servers

class ServerFormViewModel : BaseObservable() {
    private var serverModel = ServerViewModel()

    fun setForm(server: Servers) {
        serverModel.setModel(server)
    }

    @Bindable
    fun getTitle() : String {
        return serverModel.title ?: ""
    }

    fun setTitle(value: String) {
        serverModel.title = value
        notifyPropertyChanged(BR.item)
    }

    @Bindable
    fun getUrl() : String {
        return serverModel.url ?: ""
    }

    fun setUrl(value: String) {
        serverModel.url = value
        notifyPropertyChanged(BR.item)
    }

    @Bindable
    fun getToken() : String {
        return serverModel.token ?: ""
    }

    fun setToken(value: String) {
        serverModel.token = value
        notifyPropertyChanged(BR.item)
    }

    fun isFormValid() : Boolean {
        return !serverModel.title.isNullOrEmpty() and !serverModel.url.isNullOrEmpty() and !serverModel.token.isNullOrEmpty()
    }

    fun getModel(id: Long?) : Servers {
        return serverModel.title?.let { serverModel.url?.let { it1 ->
            serverModel.token?.let { it2 ->
                Servers(id!!, it,
                    it1, it2
                )
            }
        } }!!
    }
}