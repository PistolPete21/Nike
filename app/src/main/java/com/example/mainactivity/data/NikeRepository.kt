package com.example.mainactivity.data

class NikeRepository(private val nikeApiService: NikeApiService) {
    suspend fun getData() = nikeApiService.getData()
}