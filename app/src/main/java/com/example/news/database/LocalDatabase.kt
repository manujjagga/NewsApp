package com.example.news.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.news.model.ArticlesItem

@Database(
    entities = [ArticlesItem::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(DataConverter::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun mainDao(): MainDao

}