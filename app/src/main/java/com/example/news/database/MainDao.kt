package com.example.news.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.news.model.ArticlesItem

@Dao
interface MainDao {

    @Query("select * from NewsMaster")
    fun fetchNewsItems():LiveData<List<ArticlesItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewsItems(list: List<ArticlesItem>)
}