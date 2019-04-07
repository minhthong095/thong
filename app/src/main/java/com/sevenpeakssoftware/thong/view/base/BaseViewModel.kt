package com.sevenpeakssoftware.thong.view.base

import androidx.lifecycle.ViewModel
import com.sevenpeakssoftware.thong.config.database.DatabaseHelper
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    val _disposable = CompositeDisposable()

    override fun onCleared() {
        _disposable.dispose()
        super.onCleared()
    }

    open fun react() {}

    fun getDisposable() = _disposable
}