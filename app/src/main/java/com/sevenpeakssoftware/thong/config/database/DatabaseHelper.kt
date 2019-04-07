package com.sevenpeakssoftware.thong.config.database

import com.sevenpeakssoftware.thong.config.model.Artical
import io.reactivex.Observable
import java.util.concurrent.Callable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseHelper : IDatabaseHelper {

    private val mDb: Database

    @Inject
    constructor(db: Database) {
        mDb = db
    }

    override fun getAllArtical(): Observable<List<Artical>> {
        return Observable.create { emitter ->
            emitter.onNext(mDb.tableArtical().loadAll())
        }
    }

    override fun insertAllArtical(articals: List<Artical>): Observable<Boolean> {
        return Observable.fromCallable(object : Callable<Boolean> {
            override fun call(): Boolean {
                mDb.tableArtical().insertAll(articals)
                return true
            }
        })
    }

    override fun insertArtical(artical: Artical): Observable<Boolean> {
        return Observable.fromCallable(object : Callable<Boolean> {
            override fun call(): Boolean {
                mDb.tableArtical().insert(artical)
                return true
            }
        })
    }
}