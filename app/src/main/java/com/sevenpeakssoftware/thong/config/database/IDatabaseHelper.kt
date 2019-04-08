package com.sevenpeakssoftware.thong.config.database

import com.sevenpeakssoftware.thong.config.model.Article
import io.reactivex.Observable

interface IDatabaseHelper {
    fun getAllArticle() : Observable<List<Article>>
    fun insertArticle(article:Article)
    fun deleteAllArticle()

}