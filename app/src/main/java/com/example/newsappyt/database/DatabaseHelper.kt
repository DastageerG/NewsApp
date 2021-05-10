package com.example.newsappyt.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsappyt.model.Article

@Database(entities = [Article::class ],exportSchema = false,version = 1)
@TypeConverters(Converter::class)
abstract class DatabaseHelper : RoomDatabase()
{

    abstract fun articlesDao():ArticlesDao?

    companion object
    {
        @Volatile
        private var Instance : DatabaseHelper? = null

        fun getInstance(context: Context) : DatabaseHelper?
        {
            if(Instance == null)
            {
                synchronized(this)
                {
                    Instance = Room.databaseBuilder(context.applicationContext,DatabaseHelper::class.java,"FavArticleDatabase").build()
                } // synchronized closed

            } // if closed
            return Instance
        } // fun getInstance

    } // companion object



} // class closed