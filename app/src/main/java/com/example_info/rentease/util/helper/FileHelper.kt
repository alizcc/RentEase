package com.example_info.rentease.util.helper

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream

object FileHelper {

    fun saveImageToCache(context: Context, imageUri: Uri): Uri? {
        return try {
            val inputStream = context.contentResolver.openInputStream(imageUri)
            inputStream?.use { input ->
                val fileName = "cached_image_${System.currentTimeMillis()}.jpg"
                val cacheDir = context.cacheDir
                val file = File(cacheDir, fileName)
                val outputStream = FileOutputStream(file)
                outputStream.use { output ->
                    input.copyTo(output)
                }
                // Image saved to cache directory
                Log.d("CacheSave", "Image saved at: ${file.absolutePath}")
                file.toUri()
            } ?: run {
                Log.e("CacheSave", "Failed to open InputStream")
                null
            }
        } catch (e: Exception) {
            Log.e("CacheSave", "Failed: $e")
            null
        }
    }

    fun deleteCachedImageByUri(uri: Uri): Boolean {
        // Convert the URI to a File object
        val file = File(uri.path ?: return false)

        // Check if the file exists and delete it
        if (file.exists()) {
            val deleted = file.delete()
            if (deleted) {
                Log.d("CacheDelete", "Image deleted: ${file.absolutePath}")
            } else {
                Log.e("CacheDelete", "Failed to delete image: ${file.absolutePath}")
            }
            return deleted
        } else {
            Log.e("CacheDelete", "File not found: ${file.absolutePath}")
            return false
        }
    }

}
