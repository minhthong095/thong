package com.sevenpeakssoftware.thong.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sevenpeakssoftware.thong.view.main.IMainService
import com.sevenpeakssoftware.thong.view.main.MainViewModel
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelProviderFactory : ViewModelProvider.NewInstanceFactory {

    private val mRetrofit: Retrofit

    @Inject
    constructor(retrofit: Retrofit) {
        mRetrofit = retrofit
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(mRetrofit.create(IMainService::class.java)) as T

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}