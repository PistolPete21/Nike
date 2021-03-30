package com.example.mainactivity.data

import com.example.mainactivity.data.response.BaseResponse
import retrofit2.http.GET

interface NikeApiService {
    @GET("svc/mostpopular/v2/viewed/1.json?api-key=${Constants.API_KEY}")
    suspend fun getData(): BaseResponse
}