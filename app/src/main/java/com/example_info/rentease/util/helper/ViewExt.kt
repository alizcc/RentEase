package com.example_info.rentease.util.helper

import android.widget.EditText

/**
 * Created by AP-Jake
 * on 09/06/2024
 */


fun EditText.showErrorAndFocus(error: String) {
    this.error = error
    this.requestFocus()
}
