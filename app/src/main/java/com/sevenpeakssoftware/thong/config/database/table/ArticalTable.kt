package com.sevenpeakssoftware.thong.config.database.table

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sevenpeakssoftware.thong.config.model.Artical

import kotlin.collections.List

@Dao
interface ArticalTable {
    @Query("SELECT * FROM tblArtical ORDER BY id DESC")
    fun loadAll(): List<Artical>;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articals: List<Artical>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(artical: Artical)
}