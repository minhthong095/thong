package com.sevenpeakssoftware.thong.view.main

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.WindowManager
import com.sevenpeakssoftware.thong.R
import com.sevenpeakssoftware.thong.databinding.ItemArticalBinding
import com.sevenpeakssoftware.thong.view.base.BaseRecycleViewAdapter
import com.sevenpeakssoftware.thong.view.base.BaseViewModel
import com.sevenpeakssoftware.thong.view.base.RecycleViewCell
import com.sevenpeakssoftware.thong.view.main.item.ArticalCellViewModel
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject

class MainViewModel(val mMainService: IMainService) : BaseViewModel() {

    val bindAdapter = MainAdapter()

    fun fetchArtical() {
        getDisposable().add(
            mMainService.getAllArtical()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .takeWhile { response -> response.content != null && response.content!!.isNotEmpty() }
                .subscribe({ response ->
                    println("XXXX")
                    bindAdapter.itemSource.addAll(
                        response.content!!.map {
                            ArticalCellViewModel(it)
                        }
                    )
                }, { throwable ->
                    println("FAILED")
                    println(throwable)
                })
        )
    }
}

class MainAdapter : BaseRecycleViewAdapter<ItemArticalBinding, ArticalCellViewModel>() {

    override fun getLayoutId(viewType: Int) = R.layout.item_artical

    override fun getRatioHeight() = 0.6f
}