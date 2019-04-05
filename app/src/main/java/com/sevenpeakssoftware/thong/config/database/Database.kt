package com.sevenpeakssoftware.thong.config.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sevenpeakssoftware.thong.config.database.table.CarTable
import com.sevenpeakssoftware.thong.config.model.Car

@Database(entities = arrayOf(Car::class), version = 1)
abstract class Database : RoomDatabase() {

    abstract fun tableCar(): CarTable

}