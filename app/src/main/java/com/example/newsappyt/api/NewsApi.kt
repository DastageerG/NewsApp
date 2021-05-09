package com.example.newsappyt.api

import com.example.newsappyt.model.NewsResponse
import com.example.newsappyt.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi
{

    @GET("v2/top-headlines")
    suspend fun getBreakingNews
            (
                    @Query("country")
                    country:String ="us",
                    @Query("category")
                    category: String ="business",
                    @Query("apiKey")
                    apiKey:String = Constants.apiKey): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchForNews
            (
                    @Query("q")
                    searchQuery:String,
                    @Query("page")
                    page: Int =1,
                    @Query("apiKey")
                    apiKey:String = Constants.apiKey): Response<NewsResponse>

}