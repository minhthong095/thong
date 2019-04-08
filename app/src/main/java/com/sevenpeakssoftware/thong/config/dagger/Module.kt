package com.sevenpeakssoftware.thong.config.dagger

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.sevenpeakssoftware.thong.config.Constant
import com.sevenpeakssoftware.thong.config.ViewModelProviderFactory
import com.sevenpeakssoftware.thong.config.database.Database
import com.sevenpeakssoftware.thong.config.database.DatabaseHelper
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Module
class Module {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context =
        application

    @Provides
    @Singleton
    fun provideDatabase(context: Context): Database =
        Room.databaseBuilder(context, Database::class.java, Constant.DB_NAME)
            .fallbackToDestructiveMigrationFrom(Constant.DB_VERSION - 1)
            .build()

    @Provides
    @Singleton
    fun provideService(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    fun buildOkHttpClient(context: Context): OkHttpClient {
        val TIME_OUT = 10L
        return OkHttpClient.Builder()
            .callTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build()
    }
}
