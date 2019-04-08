package com.sevenpeakssoftware.thong.config.database

import com.sevenpeakssoftware.thong.config.model.Article
import com.sevenpeakssoftware.thong.config.model.Content
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

    override fun insertContents(content: List<Content>) =
        mDb.tableContent().insertAll(content)

    override fun getAllArticle(): Observable<List<Article>> =
        Observable.create { emitter ->
            emitter.onNext(mDb.tableArticle().loadAll())
        }

    override fun insertArticle(article: Article) =
        mDb.tableArticle().insert(article)

    override fun deleteAllArticle() =
        mDb.tableArticle().deleteAll()
}