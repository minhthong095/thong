package com.sevenpeakssoftware.thong.config.database

import com.sevenpeakssoftware.thong.config.model.Artical
import io.reactivex.Observable
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

    override fun insertArtical(artical: Artical) {
        mDb.tableArtical().insert(artical)
    }

    override fun deleteAllArtical() {
        mDb.tableArtical().deleteAll()
    }
}