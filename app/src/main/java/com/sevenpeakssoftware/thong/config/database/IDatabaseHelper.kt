package com.sevenpeakssoftware.thong.config.database

import com.sevenpeakssoftware.thong.config.model.Article
import com.sevenpeakssoftware.thong.config.model.Content
import io.reactivex.Observable

interface IDatabaseHelper {

    // Table Article
    fun getAllArticle(): Observable<List<Article>>
    fun insertArticle(article: Article)
    fun deleteAllArticle()

    // Table Content
    fun insertAllContent(contents: List<Content>)
    fun getAllContent(): Observable<List<Content>>

}