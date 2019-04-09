package com.sevenpeakssoftware.thong.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.sevenpeakssoftware.thong.R
import io.reactivex.subjects.PublishSubject

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("bind:image")
    fun bindUrl(imageView: ImageView, imageData: Any?) {
        val context = imageView.context

        val loader = if (imageData is ByteArray) imageData as ByteArray else imageData as String

        Glide.with(context)
            .load(loader)
            .error(R.drawable.default_thumb)
            .centerCrop()
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("bind:hourArticle")
    fun bindHourArticle(textView: TextView, dateTime: String?) {
        if (dateTime == null)
            textView.setText("")
        else if (android.text.format.DateFormat.is24HourFormat(textView.context))
            textView.setText(dateTime.toOtherTimeFormat(toFormat = ", HH:mm"))
        else
            textView.setText(dateTime.toOtherTimeFormat(toFormat = ", hh:mm a"))
    }

    @JvmStatic
    @BindingAdapter("bind:isSwipe")
    fun bindIsSwipe(swipeRefreshLayout: SwipeRefreshLayout, swipeObserver: PublishSubject<Boolean>) {
        swipeObserver.subscribe { swipeRefreshLayout.isRefreshing = it }
    }

    @JvmStatic
    @BindingAdapter("bind:onSwipe")
    fun bindOnSwipe(swipeRefreshLayout: SwipeRefreshLayout, onSwipeObservable: PublishSubject<SwipeRefreshLayout>) {
        swipeRefreshLayout.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                onSwipeObservable.onNext(swipeRefreshLayout)
            }
        })
    }
}