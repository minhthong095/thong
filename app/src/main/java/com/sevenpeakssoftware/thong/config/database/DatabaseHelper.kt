package com.sevenpeakssoftware.thong.config.database

import com.sevenpeakssoftware.thong.config.model.Artical
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseHelper(val mDb: Database) : IDatabaseHelper {

    override fun getAllArtical(): Observable<List<Artical>> {
        return Observable.create { emitter ->
            emitter.onNext(mDb.tableArtical().loadAll())
        }
    }

}