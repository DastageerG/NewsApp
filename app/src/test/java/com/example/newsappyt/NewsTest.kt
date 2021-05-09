package com.example.newsappyt

import com.example.newsappyt.api.NewsClient
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class NewsTest
{
    private val newsClient:NewsClient = NewsClient()

    @Test
    fun GetBreakingNews()
    {
        runBlocking ()
        {
            val response = newsClient.api.getBreakingNews()
            Assert.assertNotNull(response.body())
        }
    }


}