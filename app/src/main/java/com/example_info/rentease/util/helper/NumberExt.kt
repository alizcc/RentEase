package com.example_info.rentease.util.helper

val Long.asCommaSeparated: String
    get() {
        val numberString = this.toString()
        val reversed = numberString.reversed()
        val chunks = reversed.chunked(3)
        return chunks.joinToString(",").reversed()
    }
