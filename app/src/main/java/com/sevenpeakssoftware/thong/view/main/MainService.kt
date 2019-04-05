package com.sevenpeakssoftware.thong.view.main

import com.sevenpeakssoftware.thong.config.model.ArticalResponse
import io.reactivex.Observable
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

interface IMainService {
//    fun getAllArtical() : Observable<List<ArticalResponse>>
}

@Singleton
class MainService : IMainService {

//    @Inject
//    private lateinit var _service : Retrofit

//    override fun getAllArtical() =
//        _service.create(IMainService::class.java).getAllArtical()
}