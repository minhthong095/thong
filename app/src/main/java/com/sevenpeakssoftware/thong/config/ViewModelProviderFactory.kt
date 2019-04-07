package com.sevenpeakssoftware.thong.config

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sevenpeakssoftware.thong.config.database.Database
import com.sevenpeakssoftware.thong.config.database.DatabaseHelper
import com.sevenpeakssoftware.thong.view.main.IMainService
import com.sevenpeakssoftware.thong.view.main.MainViewModel
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelProviderFactory : ViewModelProvider.NewInstanceFactory {

    private val mRetrofit: Retrofit
    private val mDbHelper: DatabaseHelper
    private val mContext: Context

    @Inject
    constructor(retrofit: Retrofit, dbHelper: DatabaseHelper, context: Context) {
        mRetrofit = retrofit
        mDbHelper = dbHelper
        mContext = context
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(mRetrofit.create(IMainService::class.java), mDbHelper, mContext) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}