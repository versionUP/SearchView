package com.example.prashanth.repository

import android.view.View
import android.widget.ImageView
import com.example.prashanth.models.ImageDownloaderOptions

interface ImageDownloaderRepo {


    /**
     * Downloads an image from the given url in the [imageOptions] provided and sets it to the given [ImageView].
     * If the image is in cache then the image is retrieved from the cache instead
     * of retrieving it from the server
     *
     * @param imageOptions to be used when downloading the image
     * @param imageView the [ImageView] used to show the image
     * @param parentView the the parent of the given [imageView]
     */
    fun displayImage(
        imageOptions: ImageDownloaderOptions,
        imageView: ImageView,
        parentView: View
    )
}
