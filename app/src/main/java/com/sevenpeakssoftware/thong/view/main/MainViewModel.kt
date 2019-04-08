package com.sevenpeakssoftware.thong.view.main

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.sevenpeakssoftware.thong.R
import com.sevenpeakssoftware.thong.config.database.DatabaseHelper
import com.sevenpeakssoftware.thong.config.model.Artical
import com.sevenpeakssoftware.thong.config.model.ArticalResponse
import com.sevenpeakssoftware.thong.databinding.ItemArticalBinding
import com.sevenpeakssoftware.thong.view.base.BaseRecycleViewAdapter
import com.sevenpeakssoftware.thong.view.base.BaseViewModel
import com.sevenpeakssoftware.thong.view.main.item.ArticalCellViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import com.bumptech.glide.request.transition.Transition
import io.reactivex.subjects.PublishSubject


class MainViewModel : BaseViewModel {

    val bindAdapter = MainAdapter()
    val bindIsSwipe: PublishSubject<Boolean> = PublishSubject.create()
    val bindOnSwipeRefreshLayout: PublishSubject<SwipeRefreshLayout> = PublishSubject.create()
    val bindIsShowNoData = ObservableField<Int>(View.GONE)

    private val mMainService: IMainService
    private val mDbHelper: DatabaseHelper
    private val mContext: Context

    constructor(mainService: IMainService, dbHelper: DatabaseHelper, context: Context) {
        mMainService = mainService
        mDbHelper = dbHelper
        mContext = context

        _fetchArticals()
        _subscribeOnRefresh()
    }

    private fun _subscribeOnRefresh() {
        getDisposable().add(
            bindOnSwipeRefreshLayout
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view ->
                    _fetchArticals()
                }
        )
    }

    private fun _fetchArticals() {
        getDisposable().add(
            mMainService.getAllArtical()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    bindIsShowNoData.set(View.GONE)
                    bindIsSwipe.onNext(false)
                    bindAdapter.itemSource.clear()
                    bindAdapter.itemSource.addAll(response.content!!.map(::ArticalCellViewModel))
                    _saveArticals(response.content!!)
                }, { throwable ->
                    Toast.makeText(mContext, "Load new data failed.", Toast.LENGTH_LONG).show()
                    Log.e("FAIED", throwable.toString())
                    bindIsSwipe.onNext(false)
                    if (bindAdapter.itemSource.size == 0)
                        _showOfflineArticals()
                })
        )
    }

    private fun _showOfflineArticals() {
        getDisposable().add(
            mDbHelper.getAllArtical()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ offlineData ->
                    if (offlineData.isEmpty())
                        bindIsShowNoData.set(View.VISIBLE)
                    else
                        bindAdapter.itemSource.addAll(offlineData.map(::ArticalCellViewModel))
                }, { throwable ->
                    bindIsShowNoData.set(View.VISIBLE)
                })
        )
    }

    private fun _saveArticals(articalsResponse: List<ArticalResponse>) {

        _deleteAllArticals()

        articalsResponse.forEach {
            if (it.image != null)
                _saveItemWithImage(it)
            else
                _saveItemWihtoutImage(it)
        }
    }

    private fun _deleteAllArticals() {
        getDisposable().add(
            Observable.just(mDbHelper)
                .subscribeOn(Schedulers.io())
                .subscribe { db -> mDbHelper.deleteAllArtical() }
        )
    }

    private fun _saveItem(artical: Artical) {
        getDisposable().add(
            Observable.just(mDbHelper)
                .subscribeOn(Schedulers.io())
                .subscribe { db -> db.insertArtical(artical) }
        )
    }

    private fun _saveItemWihtoutImage(articalResponse: ArticalResponse) {
        _saveItem(
            Artical(
                id = articalResponse.id,
                title = articalResponse.title,
                ingress = articalResponse.ingress,
                dateTime = articalResponse.dateTime,
                created = articalResponse.created,
                changed = articalResponse.changed
            )
        )
    }

    private fun _saveItemWithImage(articalResponse: ArticalResponse) {
        Glide.with(mContext)
            .asBitmap()
            .load(articalResponse.image)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(bmp: Bitmap, transition: Transition<in Bitmap>?) {

                    val byteArray = ByteArrayOutputStream()
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArray)
                    bmp.recycle()

                    _saveItem(
                        Artical(
                            id = articalResponse.id,
                            title = articalResponse.title,
                            ingress = articalResponse.ingress,
                            dateTime = articalResponse.dateTime,
                            created = articalResponse.created,
                            changed = articalResponse.changed,
                            image = byteArray.toByteArray()
                        )
                    )
                }
            })
    }
}

class MainAdapter : BaseRecycleViewAdapter<ItemArticalBinding, ArticalCellViewModel>() {
    override fun getLayoutId(viewType: Int) = R.layout.item_artical
}