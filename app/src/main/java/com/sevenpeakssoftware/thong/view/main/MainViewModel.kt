package com.sevenpeakssoftware.thong.view.main

import com.sevenpeakssoftware.thong.R
import com.sevenpeakssoftware.thong.view.base.BaseRecycleViewAdapter
import com.sevenpeakssoftware.thong.view.base.BaseViewModel
import com.sevenpeakssoftware.thong.view.main.item.ArticalCellViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.view.*
import retrofit2.Retrofit
import javax.inject.Inject

class MainViewModel(val mMainService: IMainService) : BaseViewModel() {

    val bindAdapter = MainAdapter()

    fun fetchArtical() {
        println("FETCHHH")
        getDisposable().add(
            mMainService.getAllArtical()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .takeWhile { response -> response.content != null && response.content!!.isNotEmpty() }
                .subscribe({ response ->
                    println("XXXX")
                    bindAdapter.itemSource.addAll(
                         response.content!!.map { articalResponse -> ArticalCellViewModel(articalResponse.title) }
                     )
                }, { throwable ->
                    println("FAILED")
                    println(throwable)
                })
        )
    }
}

class MainAdapter : BaseRecycleViewAdapter<ArticalCellViewModel>() {
    override fun getLayoutId(viewType: Int) = R.layout.item_artical
}