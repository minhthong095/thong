package com.sevenpeakssoftware.thong.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sevenpeakssoftware.thong.BR
import com.sevenpeakssoftware.thong.R
import com.sevenpeakssoftware.thong.databinding.ActivityMainBinding
import com.sevenpeakssoftware.thong.view.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

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