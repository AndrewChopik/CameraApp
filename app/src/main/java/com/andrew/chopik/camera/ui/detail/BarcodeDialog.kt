package com.andrew.chopik.camera.ui.detail

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import com.andrew.chopik.camera.EXTRA_BARCODE
import com.andrew.chopik.camera.R

class BarcodeDialog : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialogListener = context

        val message = savedInstanceState?.getString(EXTRA_BARCODE)

        return if (dialogListener is BarcodeDialogListener) {
            AlertDialog.Builder(activity)
                    .setMessage(message)
                    .setPositiveButton(R.string.button_ok, { dialog, id ->
                        dialogListener.onDeleteDialogPositiveClick(this)
                    })
                    .setCancelable(false)
                    .create()
        } else {
            super.onCreateDialog(savedInstanceState)
        }
    }

    interface BarcodeDialogListener {

        fun onDeleteDialogPositiveClick(dialog: AppCompatDialogFragment)
    }
}