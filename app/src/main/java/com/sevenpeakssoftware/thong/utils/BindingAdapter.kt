package com.sevenpeakssoftware.thong.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("bind:adapter")
    fun bindAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?) =
        adapter?.let { view.adapter = adapter }

//    @JvmStatic
//    @BindingAdapter("bind:picassoUrl")
//    fun bindPicassoLoad(view: ImageView, )
}