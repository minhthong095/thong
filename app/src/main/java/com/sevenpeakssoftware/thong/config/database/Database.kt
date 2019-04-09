package com.sevenpeakssoftware.thong.config.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sevenpeakssoftware.thong.config.database.table.TableArticle
import com.sevenpeakssoftware.thong.config.database.table.TableContent
import com.sevenpeakssoftware.thong.config.model.Article
import com.sevenpeakssoftware.thong.config.model.Content

@Database(entities = [Article::class, Content::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract fun tableArticle(): TableArticle

    abstract fun tableContent(): TableContent
}