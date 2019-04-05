package com.sevenpeakssoftware.thong.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sevenpeakssoftware.thong.BR
import com.sevenpeakssoftware.thong.R
import com.sevenpeakssoftware.thong.databinding.ActivityMainBinding
import com.sevenpeakssoftware.thong.view.base.BaseActivity
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

//    @Inject
//    private lateinit var _service : Retrofit

    override fun getViewModel(): MainViewModel {
        return MainViewModel()
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

}