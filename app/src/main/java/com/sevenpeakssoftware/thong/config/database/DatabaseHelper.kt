package com.sevenpeakssoftware.thong.config.database

import com.sevenpeakssoftware.thong.config.model.Artical
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseHelper : IDatabaseHelper {

    private var mDb: Database? = null

    @Inject
    constructor(db: Database) { this.mDb = db }

    override fun getAllArtical(): Observable<List<Artical>> {
        return Observable.create { emitter ->
            emitter.onNext(mDb!!.tableArtical().loadAll())
        }
    }

}