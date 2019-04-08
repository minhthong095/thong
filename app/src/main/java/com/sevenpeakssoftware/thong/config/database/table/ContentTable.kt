package com.sevenpeakssoftware.thong.config.database.table

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.sevenpeakssoftware.thong.config.model.Content

@Dao
interface ContentTable {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(contents: List<Content>)
}