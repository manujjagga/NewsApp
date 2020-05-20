package com.example.news.binding

import android.widget.ImageView
import javax.inject.Inject
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.news.R
import java.text.SimpleDateFormat
import java.util.*


class FragmentBindingAdapter @Inject constructor(val fragment: Fragment) {


    @BindingAdapter("date")
    fun setTimestamp(view: TextView, date: String?) {
        date?.apply {
            val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
            val output = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            input.parse(this)?.apply {
                view.text = output.format(this)
            }
        }
    }

    @BindingAdapter("imageUrl")
    fun bindImage(imageView: ImageView, url: String?) {
        url?.let {
            Glide.with(fragment).load(it)
                .apply(RequestOptions().fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(
                    R.drawable.ic_placeholder))
                .into(imageView)
        }
    }

    @BindingAdapter("imageUrlCenterCrop")
    fun bindImageCemterCrop(imageView: ImageView, url: String?) {
        url?.let {
            Glide.with(fragment).load(it)
                .apply(RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(
                    R.drawable.ic_placeholder))
                .into(imageView)
        }
    }

}