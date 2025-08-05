package com.example.coroutine

import retrofit2.http.GET

interface ApiService {
    @GET("users/1")
    suspend fun fetchData(): DataResponse
}