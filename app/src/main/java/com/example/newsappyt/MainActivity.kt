package com.example.newsappyt

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.fragment_breaking_news, R.id.fragment_favourite_news, R.id.fragment_search_news))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    } // onCreate closed

    val string = "\"https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=303fff6aaf4a44ba801f2d5fc8324b3a\""
    val s2 = "https://newsapi.org/v2/top_headlines?country=us&category=business&apiKey=303fff6aaf4a44ba801f2d5fc8324b3a"
} // MainActivity closed



