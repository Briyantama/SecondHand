package com.binar.secondhand.helper

import android.app.AlertDialog
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.widget.Toast
import com.binar.secondhand.R
import com.google.android.material.snackbar.Snackbar

class Notif {

    fun showToast(message : String, context: Context) =
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()

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

    fun showSnackbarGreen(
        message: String,
        action: () -> Any,
        view: View,
        resources: Resources
    ) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
            .setBackgroundTint(resources.getColor(R.color.green))
            .setActionTextColor(resources.getColor(R.color.white))
        snackBar.setAction("X") { action.invoke() }
        snackBar.show()
    }

    fun dialogPN(
        context: Context,
        judul: String,
        message: String,
        positif: String,
        negatif: String,
        action: () -> Unit
    ) {
        AlertDialog.Builder(context)
            .setTitle(judul)
            .setMessage(message)
            .setPositiveButton(positif) { _, _ -> action() }
            .setNegativeButton(negatif) { listener, _ -> listener.dismiss() }
            .show()
    }

}