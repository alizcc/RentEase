package com.example_info.rentease.util.helper

import kotlin.math.round

val Long.asCommaSeparated: String
    get() {
        val numberString = this.toString()
        val reversed = numberString.reversed()
        val chunks = reversed.chunked(3)
        return chunks.joinToString(",").reversed()
    }

fun Double.roundToDecimalPlace(place: Int): Double {
    var placer = 10.0
    for (i in 1 until place) {
        placer *= 10.0
    }
    return round(this * placer) / placer
}
