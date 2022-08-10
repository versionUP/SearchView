package com.example.prashanth.models

import android.graphics.drawable.Drawable

data class PorcupineResponse(
    val title:String?,
    val link :String?,
    val description :String?,
    val modified :String?,
    val generator :String?,
    val items:List<PorcupineItem?>?
)

data class PorcupineItem(
    val title:String?,
    val link :String?,
    val media:Pic?,
    val date_taken:String?,
    val description:String?,
    val published:String?,
    val author:String?,
    val author_id:String?,
    val tags:String?
)

data class Pic(
    val m:String?
)


/**
 * A class used to hold the options for image downloader
 *
 * @param [url] the url of the image
 */
data class ImageDownloaderOptions(
    val url: String,
    val placeholder: Drawable? = null
)
