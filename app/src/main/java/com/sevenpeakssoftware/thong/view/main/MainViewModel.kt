package com.sevenpeakssoftware.thong.view.main

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.sevenpeakssoftware.thong.R
import com.sevenpeakssoftware.thong.config.database.DatabaseHelper
import com.sevenpeakssoftware.thong.config.model.Article
import com.sevenpeakssoftware.thong.config.model.ArticleResponse
import com.sevenpeakssoftware.thong.view.base.BaseRecycleViewAdapter
import com.sevenpeakssoftware.thong.view.base.BaseViewModel
import com.sevenpeakssoftware.thong.view.main.item.ArticleCellViewModel
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
    }

    override fun react() {
        _fetchArticles()
        _subscribeOnRefresh()
    }

    private fun _subscribeOnRefresh() {
        getDisposable().add(
            bindOnSwipeRefreshLayout
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view ->
                    _fetchArticles()
                }
        )
    }

    private fun _fetchArticles() {
        bindIsSwipe.onNext(true)
        getDisposable().add(
            mMainService.getAllArticle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    bindAdapter.itemSource.clear()
                    bindAdapter.itemSource.addAll(response.content!!.map(::ArticleCellViewModel))
                    _saveArticles(response.content!!)
                    bindIsShowNoData.set(View.GONE)
                    bindIsSwipe.onNext(false)
                }, { throwable ->
                    if (throwable is java.io.InterruptedIOException || throwable is java.net.UnknownHostException)
                        Toast.makeText(mContext, "Load new data failed.", Toast.LENGTH_LONG).show()

                    Log.e("FAIED", throwable.toString())
                    bindIsSwipe.onNext(false)
                    if (bindAdapter.itemSource.size == 0)
                        _showOfflineArticles()
                })
        )
    }

    private fun _showOfflineArticles() {
        getDisposable().add(
            mDbHelper.getAllArticle()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ offlineData ->
                    if (offlineData.isEmpty())
                        bindIsShowNoData.set(View.VISIBLE)
                    else
                        bindAdapter.itemSource.addAll(offlineData.map(::ArticleCellViewModel))
                }, { throwable ->
                    bindIsShowNoData.set(View.VISIBLE)
                })
        )
    }

    private fun _saveArticles(ArticlesResponse: List<ArticleResponse>) {

        _deleteAllArticles()

        ArticlesResponse.forEach {
            if (it.image != null)
                _saveItemWithImage(it)
            else
                _saveItemWihtoutImage(it)
        }
    }

    private fun _deleteAllArticles() {
        getDisposable().add(
            Observable.just(mDbHelper)
                .subscribeOn(Schedulers.io())
                .subscribe { db -> mDbHelper.deleteAllArticle() }
        )
    }

    private fun _saveItem(article: Article) {
        getDisposable().add(
            Observable.just(mDbHelper)
                .subscribeOn(Schedulers.io())
                .subscribe { db -> db.insertArticle(article) }
        )
    }

    private fun _saveItemWihtoutImage(articleResponse: ArticleResponse) {
        _saveItem(
            Article(
                id = articleResponse.id,
                title = articleResponse.title,
                ingress = articleResponse.ingress,
                dateTime = articleResponse.dateTime,
                created = articleResponse.created,
                changed = articleResponse.changed
            )
        )
    }

    private fun _saveItemWithImage(articleResponse: ArticleResponse) {
        Glide.with(mContext)
            .asBitmap()
            .load(articleResponse.image)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(bmp: Bitmap, transition: Transition<in Bitmap>?) {

                    val byteArray = ByteArrayOutputStream()
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArray)
                    bmp.recycle()

                    _saveItem(
                        Article(
                            id = articleResponse.id,
                            title = articleResponse.title,
                            ingress = articleResponse.ingress,
                            dateTime = articleResponse.dateTime,
                            created = articleResponse.created,
                            changed = articleResponse.changed,
                            image = byteArray.toByteArray()
                        )
                    )
                }
            })
    }
}

class MainAdapter : BaseRecycleViewAdapter<com.sevenpeakssoftware.thong.databinding.ItemArticleBinding, ArticleCellViewModel>() {
    override fun getLayoutId(viewType: Int) = R.layout.item_article
}