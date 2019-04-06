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

//    @Inject
//    lateinit var mDb: Database
//
//    private val mDbHelper = DatabaseHelper(mDb)

    private lateinit var mViewBinding: T

    abstract fun getViewModel(): V

    abstract fun getBindingVariable(): Int

    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        _runViewBinding()
    }

    fun getBinding() = mViewBinding

    private fun _runViewBinding() {
        mViewBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewBinding.setVariable(getBindingVariable(), getViewModel())
        mViewBinding.executePendingBindings()
    }
}