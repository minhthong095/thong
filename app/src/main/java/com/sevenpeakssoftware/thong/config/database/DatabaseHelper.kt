package com.sevenpeakssoftware.thong.config.database

import com.sevenpeakssoftware.thong.config.model.Article
import com.sevenpeakssoftware.thong.config.model.Content
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseHelper @Inject constructor(private val mDb: Database) : IDatabaseHelper {

    //////////////////////////
    ///// TABLE ARTICLE //////
    /////////////////////////

    override fun getAllArticle(): Observable<List<Article>> {
        return Observable.create { emitter ->
            emitter.onNext(mDb.tableArticle().loadAll())
        }
    }

    override fun insertArticle(article: Article) =
        mDb.tableArticle().insert(article)

    override fun deleteAllArticle() =
        mDb.tableArticle().deleteAll()


    //////////////////////////
    ///// TABLE CONTENT //////
    /////////////////////////

    override fun insertAllContent(contents: List<Content>) =
        mDb.tableContent().insertAll(contents)

    override fun getAllContent(): Observable<List<Content>> {
        return Observable.create { emitter ->
            emitter.onNext(mDb.tableContent().loadAll())
        }
    }
}