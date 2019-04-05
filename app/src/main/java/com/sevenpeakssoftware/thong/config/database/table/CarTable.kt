package com.sevenpeakssoftware.thong.config.database.table

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sevenpeakssoftware.thong.config.model.Car

import kotlin.collections.List

@Dao
interface CarTable {
    @Query("SELECT * FROM tblCar")
    fun loadAll(): List<Car>;
}