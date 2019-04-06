package com.sevenpeakssoftware.thong.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sevenpeakssoftware.thong.R
import com.squareup.picasso.Picasso
import io.reactivex.subjects.BehaviorSubject

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("bind:adapter")
    fun bindAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?) =
        adapter?.let { view.adapter = adapter }

    @JvmStatic
    @BindingAdapter("bind:url")
    fun bindUrl(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(R.drawable.default_thumb)
            .error(R.drawable.default_thumb)
            .centerCrop()
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("bind:textCaplocks")
    fun bindAdapter(view: TextView, text: String) {
        view.setText(text.capitalize())
    }
}