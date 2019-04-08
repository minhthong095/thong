package com.sevenpeakssoftware.thong.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.sevenpeakssoftware.thong.R
import com.squareup.picasso.Picasso
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("bind:adapter")
    fun bindAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?) =
        adapter?.let { view.adapter = adapter }

    @JvmStatic
    @BindingAdapter("bind:urlArtical")
    fun bindUrl(imageView: ImageView, url: String?) {
        val context = imageView.context

//        val density = context.resources.displayMetrics.density
//        val height = (context.getSize().y / density).toInt()
//        val width = (context.getSize().x / density).toInt()
//        val height = context.getSize().y / 2
//        val width = context.getSize().x
//        imageView.layoutParams.width = width
//        imageView.layoutParams.height = height

        Glide.with(context)
            .load(url)
            .error(R.drawable.default_thumb)
            .centerCrop()
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("bind:byteArtical")
    fun bindByte(imageView: ImageView, byteArray: ByteArray?) {
        val context = imageView.context
        Glide.with(context)
            .load(byteArray)
            .error(R.drawable.default_thumb)
            .centerCrop()
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("bind:hourArtical")
    fun bindHourArtical(textView: TextView, dateTime: String?) {
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