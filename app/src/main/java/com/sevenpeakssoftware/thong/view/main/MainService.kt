package com.sevenpeakssoftware.thong.view.main

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

interface IMainService {
    fun getAllCar()
}

@Singleton
class MainService : IMainService {

//    @Inject
//    var _service : Retrofit?

    override fun getAllCar() {

    }
}