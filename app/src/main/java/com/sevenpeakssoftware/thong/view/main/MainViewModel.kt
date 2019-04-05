package com.sevenpeakssoftware.thong.view.main

import com.sevenpeakssoftware.thong.view.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel : BaseViewModel {
    constructor() {
        fetchArtical()
    }

    fun fetchArtical() {
//        val mainService = MainService()
//        mainService.getAllArtical()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe { response ->
//                println(response)
//            }
    }
}