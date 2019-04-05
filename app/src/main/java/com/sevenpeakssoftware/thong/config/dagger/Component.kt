package com.sevenpeakssoftware.thong.config.dagger

import android.app.Application
import com.sevenpeakssoftware.thong.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class, Module::class, ViewBuilder::class))
interface Component {
    fun inject(app: App)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): com.sevenpeakssoftware.thong.config.dagger.Component
    }
}