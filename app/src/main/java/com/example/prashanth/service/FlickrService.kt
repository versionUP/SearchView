package com.example.prashanth.service

import com.example.prashanth.models.PorcupineResponse
import retrofit2.Response
import retrofit2.http.GET

interface FlickrService {

    /**
     * API service to get get porcupine list
     */
    @GET("photos_public.gne?format=json&nojsoncallback=1&tags=porcupine")
    suspend fun getPorcupineData():Response<PorcupineResponse>
}


//https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1&tags=porcupine
