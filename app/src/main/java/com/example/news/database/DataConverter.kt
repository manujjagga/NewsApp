package com.example.news.database

import androidx.room.TypeConverter
import com.example.news.model.Source
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {

    @TypeConverter
    fun fromString(value: String?): Source? {
        val listType = object : TypeToken<Source>() {
        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: Source?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}