package com.example.newsappyt.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Entity(tableName = "FavouriteArticles")


@JsonClass(generateAdapter = true)

data class Article(

    @PrimaryKey(autoGenerate = true) var id:Int?,
    @Json(name = "author")
    val author: String?,
    @Json(name = "content")
    val content: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "publishedAt")
    val publishedAt: String,
    @Json(name = "source")
    val source: Source,
    @Json(name = "title")
    val title: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "urlToImage")
    val urlToImage: String?
) : Serializable