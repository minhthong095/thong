package com.sevenpeakssoftware.thong.view.main

import android.os.Bundle
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModelProviders
import com.sevenpeakssoftware.thong.BR
import com.sevenpeakssoftware.thong.R
import com.sevenpeakssoftware.thong.config.ViewModelProviderFactory
import com.sevenpeakssoftware.thong.config.database.DatabaseHelper
import com.sevenpeakssoftware.thong.databinding.ActivityMainBinding
import com.sevenpeakssoftware.thong.databinding.ItemArticleBinding
import com.sevenpeakssoftware.thong.view.base.BaseActivity
import com.sevenpeakssoftware.thong.view.base.BaseRecycleViewAdapter
import com.sevenpeakssoftware.thong.view.main.item.ArticleCellViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    @Inject lateinit var mFactory: ViewModelProviderFactory

    @Inject lateinit var mDb: DatabaseHelper

    private lateinit var mViewModel : MainViewModel

    override fun getViewModel(): MainViewModel {
        mViewModel = ViewModelProviders.of(this, mFactory).get(MainViewModel::class.java)
        return mViewModel
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // MainAdapter have to put here to avoid MEMORY LEAK
        viewBinding.rvArticle.adapter = MainAdapter(mViewModel.bindItemSource)
    }
}

class MainAdapter(source: ObservableArrayList<ArticleCellViewModel>) :
    BaseRecycleViewAdapter<ItemArticleBinding, ArticleCellViewModel>(source) {

    override fun getLayoutId(viewType: Int) = R.layout.item_article
}