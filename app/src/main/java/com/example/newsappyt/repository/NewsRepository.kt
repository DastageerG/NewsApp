package com.example.newsappyt.repository

import androidx.lifecycle.LiveData
import com.example.newsappyt.api.NewsClient
import com.example.newsappyt.database.ArticlesDao
import com.example.newsappyt.model.Article
import com.example.newsappyt.model.NewsResponse
import retrofit2.Response

class NewsRepository()
{
    private var articlesDao:ArticlesDao? = null
    private var api:NewsClient? = null

    fun setArticlesDao(articlesDao: ArticlesDao?)
    {
        this.articlesDao = articlesDao
    }

    fun setNewsClient(newsClient: NewsClient?)
    {
        this.api = newsClient
    }


    suspend fun getBreakingNews() : Response<NewsResponse>?
    {
        return api?.newsApi?.getBreakingNews()
    }


    suspend fun searchNews(searchQuery:String) : Response<NewsResponse>?
    {
        return api?.newsApi?.searchForNews(searchQuery)
    }

     // insert your favourite Articles to Database

    suspend fun insertToDatabase(article:Article?)
    {
        articlesDao?.insertArtical(article)
    }

    fun getAllFavouriteArticles() : LiveData<List<Article>>?
    {
        return articlesDao?.getAllFavouriteArticles()
    }

    suspend fun deleteArticle(article: Article?)
    {
        articlesDao?.deleteArtical(article)
    }




} // NewRepository