package com.example.newsappyt.api

import com.example.newsappyt.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class NewsClient
{



    private val retrofit:Retrofit = Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    val newsApi:NewsApi = retrofit.create(NewsApi::class.java)

}