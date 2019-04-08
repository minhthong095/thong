package com.sevenpeakssoftware.thong.config.database

import com.sevenpeakssoftware.thong.config.model.Article
import com.sevenpeakssoftware.thong.config.model.Content
import io.reactivex.Observable

interface IDatabaseHelper {
    fun getAllArticle(): Observable<List<Article>>
    fun insertArticle(article: Article)
    fun deleteAllArticle()
    fun insertContents(content: List<Content>)
}