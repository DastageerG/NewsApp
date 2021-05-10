package com.example.newsappyt.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsappyt.api.NewsClient
import com.example.newsappyt.database.ArticlesDao
import com.example.newsappyt.database.DatabaseHelper
import com.example.newsappyt.model.Article
import com.example.newsappyt.model.NewsResponse
import com.example.newsappyt.model.NewsResponseJsonAdapter
import com.example.newsappyt.repository.NewsRepository
import com.example.newsappyt.utils.NewsApplication
import com.example.newsappyt.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(application: Application) : AndroidViewModel(application)
{

    private var newsRepository:NewsRepository
    private var articlesDao: ArticlesDao? = null
    private var newsClient:NewsClient? = null

    val breakingNews:MutableLiveData<com.example.newsappyt.utils.Resource<NewsResponse>> = MutableLiveData()
    val searchNews:MutableLiveData<com.example.newsappyt.utils.Resource<NewsResponse>> = MutableLiveData()

    init
    {
       articlesDao = DatabaseHelper.getInstance(application.applicationContext)?.articlesDao()
       newsClient = NewsClient()
        newsRepository = NewsRepository()
        newsRepository.setArticlesDao(articlesDao)
        newsRepository.setNewsClient(newsClient)
        getBreakingNews()
    }

    fun getBreakingNews()
    {
        viewModelScope.launch()
        {

            breakingNews.postValue(com.example.newsappyt.utils.Resource.Loading())
            try
            {
                if(hasInternetConnection())
                {
                    val response = newsRepository.getBreakingNews()
                    breakingNews.postValue(handleBreakingNewsResponse(response))
                }
                else
                {
                    breakingNews.postValue(Resource.Error("No Internet Connection"))
                }
            }catch (t:Throwable)
            {
                when(t)
                {
                    is IOException ->breakingNews.postValue(com.example.newsappyt.utils.Resource.Error("Network failed"))
                    else -> breakingNews.postValue(Resource.Error("Something went Wrong"))
                } // when closed
            } // catch closed



        } // viewModelScope closed

    } // getBreakingNews closed


    fun getSearchedNews(searchQuery:String)
    {
        viewModelScope.launch()
        {
            searchNews.postValue(com.example.newsappyt.utils.Resource.Loading())
            try
            {
                if(hasInternetConnection())
                {
                    val response = newsRepository.searchNews(searchQuery)
                    searchNews.postValue(handleSearchNewsResponse(response))
                }
                else
                {
                    searchNews.postValue(Resource.Error("No Internet Connection"))
                }
            }catch (t:Throwable)
            {
                when(t)
                {
                    is IOException ->searchNews.postValue(com.example.newsappyt.utils.Resource.Error("Network failed"))
                    else -> searchNews.postValue(Resource.Error("Something went Wrong"))
                } // when closed
            } // catch closed

        } // viewModelScope closed

    } // getSearchNews closed


    // save your favourite article
    fun insertToDatabase(article: Article?)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            newsRepository.insertToDatabase(article)
        } // viewModel Scope closed
    } // insertToDatabase closed

    // getFavouriteArticles from local database
    fun getAllFavouriteArticles() : LiveData<List<Article>>?
    {
        return newsRepository.getAllFavouriteArticles()
    }

     fun deleteArticle(article: Article?)
    {
        viewModelScope.launch (Dispatchers.IO)
        {
            newsRepository.deleteArticle(article)
        }
    }




    private fun handleBreakingNewsResponse(response: Response<NewsResponse>?):
            com.example.newsappyt.utils.Resource<NewsResponse>?
    {
        if(response!!.isSuccessful)
        {
            response.body()?.let()
            {
                return com.example.newsappyt.utils.Resource.Success(it)
            }
        }
       return com.example.newsappyt.utils.Resource.Error(response.message())
    } // handleBreakingResponse


    private fun handleSearchNewsResponse(response: Response<NewsResponse>?):
            com.example.newsappyt.utils.Resource<NewsResponse>?
    {
        if(response!!.isSuccessful)
        {
            response.body()?.let()
            {
                return com.example.newsappyt.utils.Resource.Success(it)
            }
        }
        return com.example.newsappyt.utils.Resource.Error(response.message())
    } // handleBreakingResponse


    private fun hasInternetConnection():Boolean
    {
        val connectivityManager = getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when
        {
            capabilities.hasTransport(TRANSPORT_WIFI)->true
            capabilities.hasTransport(TRANSPORT_CELLULAR)->true
            capabilities.hasTransport(TRANSPORT_ETHERNET)->true
            else->false
        }

    } // hasInternetConnection closed




} // viewModel closed