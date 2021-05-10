package com.example.newsappyt.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsappyt.model.Article

@Dao
interface ArticlesDao
{

    // insert and update
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtical(article: Article?)

    @Query("Select * from FavouriteArticles order by id desc ")
    fun getAllFavouriteArticles() : LiveData<List<Article>>


    @Delete
    suspend fun deleteArtical(article: Article?)








}