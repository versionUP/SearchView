package com.example.prashanth.repository

import com.example.prashanth.getFlickrService
import com.example.prashanth.models.PorcupineResponse
import com.example.prashanth.models.ServiceResult
import com.example.prashanth.networking.RetrofitResponseHandler
import com.example.prashanth.service.FlickrService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class PorcupineDataRepositoryImpl(
    private val service: FlickrService = getFlickrService(),
    private val ioDispatcher:CoroutineDispatcher = Dispatchers.IO
):PorcupineRepository {

    override suspend fun getPorcupineData(): ServiceResult<PorcupineResponse> {
        return withContext(ioDispatcher){
            RetrofitResponseHandler.processCall { service.getPorcupineData()}
        }
    }


}
