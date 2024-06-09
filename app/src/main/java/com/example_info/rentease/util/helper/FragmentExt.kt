package com.example_info.rentease.util.helper

import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Created by AP-Jake
 * on 09/06/2024
 */

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}
