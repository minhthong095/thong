package com.sevenpeakssoftware.thong.config.database.table

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sevenpeakssoftware.thong.config.model.Article
import com.sevenpeakssoftware.thong.config.model.Content

@Dao
interface TableContent {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(contents: List<Content>)

    @Query("SELECT * FROM tblContent")
    fun loadAll(): List<Content>;
}