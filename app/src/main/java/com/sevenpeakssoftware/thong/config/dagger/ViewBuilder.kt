package com.sevenpeakssoftware.thong.config.dagger

import com.sevenpeakssoftware.thong.view.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewBuilder {

    @ContributesAndroidInjector
    abstract fun bindMainActivity() : MainActivity

}