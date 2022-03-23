package com.bakharaalief.menuapp.data.remote.retorfit

import com.bakharaalief.menuapp.data.remote.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("complexSearch")
    suspend fun searchMenus(
        @Query("apiKey") appKey: String,
        @Query("includeNutrition") includeNutrition: Boolean,
        @Query("query") keyword: String,
    ) : SearchResponse
}