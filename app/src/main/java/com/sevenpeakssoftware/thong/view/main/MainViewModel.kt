package com.sevenpeakssoftware.thong.view.main

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
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
import com.sevenpeakssoftware.thong.config.model.Content
import io.reactivex.subjects.PublishSubject


class MainViewModel : BaseViewModel {

    val bindItemSource = ObservableArrayList<ArticleCellViewModel>()
    val bindIsSwipe: ObservableField<Boolean> = ObservableField()
    val bindOnSwipeRefreshLayout: PublishSubject<SwipeRefreshLayout> = PublishSubject.create()
    val bindShowWarning = ObservableField<String>("")

    private val mMainService: IMainService
    private val mDbHelper: DatabaseHelper
    private val mContext: Context

    constructor(mainService: IMainService, dbHelper: DatabaseHelper, context: Context) {
        mMainService = mainService
        mDbHelper = dbHelper
        mContext = context

        // These function have to put here to avoid load again when activity re-created ( Rotation )
        fetchArticles()
        subscribeOnRefresh()
    }

    /**
     * If you want fetch new data when rotate or re-created activity
     */
    override fun react() {

    }

    private fun subscribeOnRefresh() {
        getDisposable().add(
            bindOnSwipeRefreshLayout
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    fetchArticles()
                }
        )
    }

    private fun fetchArticles() {
        bindIsSwipe.set(true)
        getDisposable().add(
            mMainService.getAllArticle()
                .subscribeOn(Schedulers.io())
                .takeWhile { it.content != null && it.content!!.isNotEmpty()}
                .doOnNext { saveArticles(it!!.content!!) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->

                    bindItemSource.clear()
                    bindItemSource.addAll(response.content!!.map(::ArticleCellViewModel))

                    bindShowWarning.set("")
                    bindIsSwipe.set(false)

                }, { throwable ->

                    if (throwable is java.io.InterruptedIOException || throwable is java.net.UnknownHostException)
                        Toast.makeText(
                            mContext,
                            mContext.getString(R.string.new_data_failed),
                            Toast.LENGTH_LONG
                        ).show()

                    if (bindItemSource.size == 0)
                        showOfflineArticles()

                    bindIsSwipe.set(false)

                    Log.e("FAIED", throwable.toString())

                })
        )
    }

    private fun showOfflineArticles() {
        getDisposable().add(
            mDbHelper.getAllArticle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ offlineData ->
                    if (offlineData.isEmpty())
                        bindShowWarning.set(mContext.getString(R.string.no_offline_data))
                    else
                        bindItemSource.addAll(offlineData.map(::ArticleCellViewModel))
                }, {
                    if (bindItemSource.size == 0)
                        bindShowWarning.set(mContext.getString(R.string.failed_load_offline_data))
                })
        )
    }

    /**
     * This function is run from background to avoid skipping frame.
     */
    private fun saveArticles(listArticleResponse: List<ArticleResponse>) {
        getDisposable().add(
            Observable.just(mDbHelper)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.computation())
                .subscribe({ db ->

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
                }, { })
        )
    }
}