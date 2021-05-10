package com.example.newsappyt.database

import androidx.room.TypeConverter
import com.example.newsappyt.model.Source

class Converter
{

    @TypeConverter
    fun fromSource(source: Source) : String
    {
        return source.name
    }

    @TypeConverter
    fun fromString(name: String):Source
    {
        return Source(name,name)
    }






}