package com.example_info.rentease.util.helper

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}

fun Fragment.showConfirmationDialog(
    title: String,
    message: String,
    onCancelled: () -> Unit,
    onConfirmed: () -> Unit,
) {
    // Create an AlertDialog.Builder
    val builder = AlertDialog.Builder(requireContext())
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton("Confirm") { dialog, _ ->
            onConfirmed.invoke()
            dialog.dismiss()
        }
        .setNegativeButton("Cancel") { dialog, _ ->
            onConfirmed.invoke()
            dialog.dismiss()
        }

    // Create and show the dialog
    val dialog = builder.create()
    dialog.show()
}
