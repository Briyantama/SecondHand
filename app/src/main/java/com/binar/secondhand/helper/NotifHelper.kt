package com.binar.secondhand.helper

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class NotifHelper {

    fun showToast(message : String, context: Context){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showSnackbar(message: String, view : View) =
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()

    fun showSnackbarWithAction(
        message: String,
        actionText: String,
        action: () -> Any,
        view: View
    ) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(actionText) { action.invoke() }
        snackBar.show()
    }
}