package com.sevenpeakssoftware.thong.view.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sevenpeakssoftware.thong.BR
import com.sevenpeakssoftware.thong.R
import com.sevenpeakssoftware.thong.config.ViewModelProviderFactory
import com.sevenpeakssoftware.thong.databinding.ActivityMainBinding
import com.sevenpeakssoftware.thong.view.base.BaseActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    @Inject
    lateinit var mFactory: ViewModelProviderFactory

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
        initRecyclerView()
        fetchArticals()

    }

    fun initRecyclerView() {
        getBinding().rvArtical.layoutManager = LinearLayoutManager(this)
    }

    fun fetchArticals() {
        mViewModel.fetchArtical()
    }
}