package com.example.prashanth.repository

import com.example.prashanth.models.PorcupineResponse
import com.example.prashanth.models.ServiceResult

interface PorcupineRepository {

    /**
     * fun to get porcupines list
     */
    suspend fun getPorcupineData():ServiceResult<PorcupineResponse>
}
