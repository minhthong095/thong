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
import com.sevenpeakssoftware.thong.config.model.Content
import io.reactivex.subjects.PublishSubject
import com.bumptech.glide.request.FutureTarget
import com.sevenpeakssoftware.thong.config.model.ContentResponse


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
                .subscribeOn(Schedulers.newThread())
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

                    if (bindAdapter.itemSource.size == 0)
                        _showOfflineArticles()

                    bindIsSwipe.onNext(false)

                    Log.e("FAIED", throwable.toString())

                })
        )
    }

    private fun _showOfflineArticles() {
        getDisposable().add(
            mDbHelper.getAllArticle()
                .subscribeOn(Schedulers.io())
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

    /**
     * This function is run from background to avoid skipping frame.
     */
    private fun _saveArticles(listArticleResponse: List<ArticleResponse>) {
        getDisposable().add(
            Observable.just(mDbHelper)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.computation())
                .subscribe { db ->

                    db.deleteAllArticle()

                    listArticleResponse.forEach { articleResponse ->

                        var byteImage: ByteArray? = null
                        if (articleResponse.image != null) {
                            val futureTarget = Glide.with(mContext)
                                .`as`(ByteArray::class.java)
                                .load(articleResponse.image)
                                .submit()
                            byteImage = futureTarget.get()
                            Glide.with(mContext).clear(futureTarget)
                        }

                        db.insertArticle(
                            Article(
                                idArticle = articleResponse.id,
                                title = articleResponse.title,
                                ingress = articleResponse.ingress,
                                dateTime = articleResponse.dateTime,
                                created = articleResponse.created,
                                changed = articleResponse.changed,
                                image = byteImage
                            )
                        )

                        db.insertAllContent(
                            articleResponse.content!!.map { contentResponse ->
                                Content(articleResponse.id, contentResponse)
                            })
                    }
                }
        )
    }
}

class MainAdapter :
    BaseRecycleViewAdapter<com.sevenpeakssoftware.thong.databinding.ItemArticleBinding, ArticleCellViewModel>() {

    override fun getLayoutId(viewType: Int) = R.layout.item_article
}