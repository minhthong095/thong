package com.sevenpeakssoftware.thong.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.android.AndroidInjection

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity() {

    private var _viewBinding: T? = null

    abstract fun getViewModel(): V

    abstract fun getBindingVariable(): Int

    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        _runViewBinding()
    }

    private fun _runViewBinding() {
        _viewBinding = DataBindingUtil.setContentView(this, getLayoutId())
        _viewBinding!!.setVariable(getBindingVariable(), getViewModel())
        _viewBinding!!.executePendingBindings()
    }
}