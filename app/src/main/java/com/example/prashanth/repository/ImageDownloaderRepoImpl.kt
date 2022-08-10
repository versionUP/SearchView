package com.example.prashanth.repository

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.prashanth.models.ImageDownloaderOptions

class ImageDownloaderRepoImpl():ImageDownloaderRepo {


    override fun displayImage(
        imageOptions: ImageDownloaderOptions,
        imageView: ImageView,
        parentView: View
    ) {
        Glide.with(parentView)
            .load(imageOptions.url)
            .placeholder(imageOptions.placeholder)
            .into(imageView)
    }
}
