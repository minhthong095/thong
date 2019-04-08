package com.sevenpeakssoftware.thong.view.main

import com.sevenpeakssoftware.thong.config.EndPoint
import com.sevenpeakssoftware.thong.config.model.ListArticleResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface IMainService {
    @GET(EndPoint.GET_ARTICLE)
    fun getAllArticle() : Observable<ListArticleResponse>
}