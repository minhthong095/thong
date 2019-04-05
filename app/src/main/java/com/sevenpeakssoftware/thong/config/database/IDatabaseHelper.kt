package com.sevenpeakssoftware.thong.config.database

import com.sevenpeakssoftware.thong.config.model.Car
import io.reactivex.Observable

interface IDatabaseHelper {
    fun getAllCar() : Observable<List<Car>>
}