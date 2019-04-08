package com.sevenpeakssoftware.thong.config.database.table

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sevenpeakssoftware.thong.config.model.Article

import kotlin.collections.List

@Dao
interface ArticelTable {
    @Query("SELECT * FROM tblArticle ORDER BY id DESC")
    fun loadAll(): List<Article>;

    @Query("DELETE FROM tblArticle")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: Article)
}