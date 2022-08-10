package com.example.prashanth

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prashanth.networking.RetrofitBuilder
import com.example.prashanth.repository.ImageDownloaderRepo
import com.example.prashanth.repository.ImageDownloaderRepoImpl
import com.example.prashanth.service.FlickrService


/**
 * Fragment extension function for obtaining an instance of the view model through ViewModelProvider
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified V : ViewModel> Fragment.obtainViewModel(crossinline instance: () -> V): V {
    val factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return instance() as T
        }
    }
    return ViewModelProvider(this, factory)[V::class.java]
}



const val URL = "https://api.flickr.com/services/feeds/"

/**
 * utility function to get instance of CountryService
 */
fun getFlickrService(): FlickrService =
    RetrofitBuilder.getProviderClass(
        URL,
        service = FlickrService::class.java
    )



/**
 * @return an instance of [ImageDownloaderRepo],
 * that can be used to download images
 */
fun provideImageDownloadRepository(): ImageDownloaderRepo = ImageDownloaderRepoImpl()

