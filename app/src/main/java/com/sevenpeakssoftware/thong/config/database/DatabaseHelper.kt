package com.sevenpeakssoftware.thong.config.database

import com.sevenpeakssoftware.thong.config.model.Car
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseHelper : IDatabaseHelper {

    private var mDb: Database? = null

    @Inject
    constructor(db: Database) { this.mDb = db }

    override fun getAllCar(): Observable<List<Car>> {
        return Observable.create { emitter ->
            emitter.onNext(mDb!!.tableCar().loadAll())
        }
    }

}