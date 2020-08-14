package com.rompos.deactivator.helpers

import android.view.View
import com.google.android.material.snackbar.Snackbar

class Utils {
    companion object {
        fun snackMsg(view: View, message: String) {
            Snackbar.make(
                view,
                message,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}