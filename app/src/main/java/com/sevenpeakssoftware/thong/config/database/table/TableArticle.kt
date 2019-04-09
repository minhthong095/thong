package com.sevenpeakssoftware.thong.config.database.table

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sevenpeakssoftware.thong.config.model.Article

import kotlin.collections.List

@Dao
interface TableArticle {
    @Query("SELECT * FROM tblArticle ORDER BY IdArticle DESC")
    fun loadAll(): List<Article>;

    @Query("DELETE FROM tblArticle")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: Article)
}