package com.example.prashanth.networking

import com.example.prashanth.models.ServiceException
import com.example.prashanth.models.ServiceResult
import retrofit2.Response
import java.io.IOException

object RetrofitResponseHandler {

    const val genericMessage = "Sorry,something went wrong. Please try again."

    /**
     * function to process service call and return either ServiceResult.Success or Error
     */
    suspend fun <T> processCall(
        call: suspend () -> Response<T>
    ): ServiceResult<T> {
        return try {

            val serviceCallback = call()
            val body = serviceCallback.body()
            if (serviceCallback.isSuccessful && body != null) {
                ServiceResult.Success(body)
            } else {
                getGenericServiceError("${serviceCallback.code()}")
            }

        } catch (exception: Exception) {
            when (exception) {
                is IOException -> {
                    getConnectivityServiceError()
                }
                else -> {
                    getGenericServiceError(null)
                }
            }

        }
    }

    private fun getConnectivityServiceError(): ServiceResult.Error {
        val error = "no connectivity error"
        return ServiceResult.Error(ServiceException(null, error))
    }


    private fun getGenericServiceError(
        errorCode: String?
    ): ServiceResult.Error {
        return ServiceResult.Error(ServiceException(errorCode, genericMessage))
    }
}
