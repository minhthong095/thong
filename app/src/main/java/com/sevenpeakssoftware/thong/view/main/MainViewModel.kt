package com.sevenpeakssoftware.thong.view.main

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import com.sevenpeakssoftware.thong.R
import com.sevenpeakssoftware.thong.config.database.DatabaseHelper
import com.sevenpeakssoftware.thong.config.model.Artical
import com.sevenpeakssoftware.thong.config.model.ArticalResponse
import com.sevenpeakssoftware.thong.databinding.ItemArticalBinding
import com.sevenpeakssoftware.thong.utils.getSize
import com.sevenpeakssoftware.thong.view.base.BaseRecycleViewAdapter
import com.sevenpeakssoftware.thong.view.base.BaseViewModel
import com.sevenpeakssoftware.thong.view.base.RecycleViewCell
import com.sevenpeakssoftware.thong.view.main.item.ArticalCellViewModel
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import com.bumptech.glide.request.transition.Transition


class MainViewModel : BaseViewModel {

    val bindAdapter = MainAdapter()

    private val mMainService: IMainService
    private val mDbHelper: DatabaseHelper
    private val mContext: Context

    constructor(mainService: IMainService, dbHelper: DatabaseHelper, context: Context) {
        mMainService = mainService
        mDbHelper = dbHelper
        mContext = context

        fetchArtical()
    }

    fun fetchArtical() {
        getDisposable().add(
            mMainService.getAllArtical()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .takeWhile { response -> response.content != null && response.content!!.isNotEmpty() }
                .subscribe({ response ->
                    bindAdapter.itemSource
                        .addAll(response.content!!.map(::ArticalCellViewModel))
                    _saveArticals(response.content!!)
                }, { throwable ->
                    println("FAILED")
                    println(throwable)
                })
        )
    }

    private fun _saveArticals(articalsResponse: List<ArticalResponse>) {

        articalsResponse.forEach {
            Glide.with(mContext)
                .asBitmap()
                .load(it.image)
                .fitCenter()
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(bmp: Bitmap, transition: Transition<in Bitmap>?) {
                        println("width Raw " + bmp.width)
                        println("height Raw " + bmp.height)
                        println()

                        val byteArray = ByteArrayOutputStream()
                        bmp.compress(Bitmap.CompressFormat.PNG, 70, byteArray)
                        bmp.recycle()

                        val artical = Artical(
                            id = it.id,
                            title = it.title,
                            ingress = it.ingress,
                            dateTime = it.dateTime,
                            created = it.created,
                            changed = it.changed,
                            image = if (it.image == null) null else byteArray.toByteArray()
                        )

                        mDbHelper.insertArtical(artical)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ result ->
                                println("REsult add " + result + " " + it.content!![0]!!.type)
                            }, { throwable ->
                                println("FAILED SAVE")
                                println(throwable)
                            })
                    }
                })
        }
    }
}

class MainAdapter : BaseRecycleViewAdapter<ItemArticalBinding, ArticalCellViewModel>() {

    override fun getLayoutId(viewType: Int) = R.layout.item_artical

}