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
    @BindingAdapter("bind:urlArtical")
    fun bindUrl(imageView: ImageView, url: String) {
        val context = imageView.context

        val density = context.resources.displayMetrics.density
        val height = context.getSize().y / density
        val width = context.getSize().x / density

        Glide.with(context)
            .load(url)
            .override(context.getSize().x,context.getSize().y / 2)
            .placeholder(R.drawable.default_thumb)
            .error(R.drawable.default_thumb)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("bind:hourArtical")
    fun bindHourArtical(textView: TextView, dateTime: String?) {
        if(dateTime == null)
            textView.setText("")
        else if (android.text.format.DateFormat.is24HourFormat(textView.context))
            textView.setText(dateTime.toOtherTimeFormat(toFormat = ", HH:mm"))
        else
            textView.setText(dateTime.toOtherTimeFormat(toFormat = ", hh:mm a"))
    }
}