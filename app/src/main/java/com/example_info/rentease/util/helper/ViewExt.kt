package com.example_info.rentease.util.helper

import android.graphics.drawable.InsetDrawable
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example_info.rentease.R

/**
 * Created by AP-Jake
 * on 09/06/2024
 */


fun EditText.showErrorAndFocus(error: String) {
    this.error = error
    this.requestFocus()
}

fun ImageView.tryLoad(uriString: String?) {
    Glide
        .with(context)
        .load(uriString)
        .into(this)
        .onLoadFailed(
            InsetDrawable(ContextCompat.getDrawable(context, R.drawable.ic_person), 10, 10, 10, 10)
        )
}
