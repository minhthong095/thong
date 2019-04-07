package com.sevenpeakssoftware.thong.config.dagger

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.sevenpeakssoftware.thong.config.Constant
import com.sevenpeakssoftware.thong.config.ViewModelProviderFactory
import com.sevenpeakssoftware.thong.config.database.Database
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Module
class Module {

    @Provides
    @Singleton
    fun provideApplication(application: Application) = application

    @Provides
    @Singleton
    fun provideDatabase(context: Context): Database =
        Room.databaseBuilder(context, Database::class.java, Constant.DB_NAME)
            .fallbackToDestructiveMigrationFrom(Constant.DB_VERSION - 1)
            .build()

    @Provides
    fun provideService(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
}
