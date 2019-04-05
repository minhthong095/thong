package com.sevenpeakssoftware.thong.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sevenpeakssoftware.thong.BR
import com.sevenpeakssoftware.thong.R
import com.sevenpeakssoftware.thong.config.database.Database
import com.sevenpeakssoftware.thong.databinding.ActivityMainBinding
import com.sevenpeakssoftware.thong.view.base.BaseActivity
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    @Inject
    lateinit var retrofit: Retrofit

    private lateinit var mViewModel : MainViewModel

    override fun getViewModel(): MainViewModel {
        mViewModel = MainViewModel(retrofit.create(IMainService::class.java))
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

        mViewModel.fetchArtical()
    }
}