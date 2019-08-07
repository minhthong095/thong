package com.sevenpeakssoftware.thong.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.sevenpeakssoftware.thong.config.database.Database
import com.sevenpeakssoftware.thong.config.database.DatabaseHelper
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity() {

    lateinit var viewBinding: T


    abstract fun getViewModel(): V


    abstract fun getBindingVariable(): Int


    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        runViewBinding()
        getViewModel().react()
    }

    private fun runViewBinding() {
        viewBinding = DataBindingUtil.setContentView(this, getLayoutId())
        viewBinding.setVariable(getBindingVariable(), getViewModel())
        viewBinding.executePendingBindings()
    }
}