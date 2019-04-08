package com.sevenpeakssoftware.thong.config.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sevenpeakssoftware.thong.config.database.table.ArticelTable
import com.sevenpeakssoftware.thong.config.model.Article

@Database(entities = arrayOf(Article::class), version = 1)
abstract class Database : RoomDatabase() {

    abstract fun tableArticle(): ArticelTable

}