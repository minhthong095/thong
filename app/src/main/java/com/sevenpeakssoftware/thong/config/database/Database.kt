package com.sevenpeakssoftware.thong.config.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sevenpeakssoftware.thong.config.database.table.ArticelTable
import com.sevenpeakssoftware.thong.config.database.table.ContentTable
import com.sevenpeakssoftware.thong.config.model.Article
import com.sevenpeakssoftware.thong.config.model.Content

@Database(entities = arrayOf(Article::class, Content::class), version = 1)
abstract class Database : RoomDatabase() {

    abstract fun tableArticle(): ArticelTable

    abstract fun tableContent(): ContentTable
}