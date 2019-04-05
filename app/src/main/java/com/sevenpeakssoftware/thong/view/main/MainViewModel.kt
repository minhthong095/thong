package com.sevenpeakssoftware.thong.view.main

import com.sevenpeakssoftware.thong.view.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject

class MainViewModel(val mMainService: IMainService) : BaseViewModel() {

    fun fetchArtical() {
        println("FETCHHH")
        getDisposable().add(
            mMainService.getAllArtical()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    println("XXXX")
                    println(response)
                }, { throwable ->
                    println("FAILED")
                    println(throwable)
                })
        )
    }
}