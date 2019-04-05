package com.sevenpeakssoftware.thong.view.main

import com.sevenpeakssoftware.thong.config.EndPoint
import com.sevenpeakssoftware.thong.config.model.ListArticalResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface IMainService {
    @GET(EndPoint.GET_ARTICAL)
    fun getAllArtical() : Observable<ListArticalResponse>
}