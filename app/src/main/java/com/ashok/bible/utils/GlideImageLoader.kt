package com.ashok.bible.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.ashok.bible.R
import com.ashok.bible.databinding.DataBindingAdapter
import com.bumptech.glide.Glide
import lv.chi.photopicker.loader.ImageLoader

class GlideImageLoader: ImageLoader {

    override fun loadImage(context: Context, view: ImageView, uri: Uri) {
        Glide.with(context)
            .load(uri)
            .asBitmap()
            .placeholder(R.drawable.place_holder)
            .centerCrop()
            .into(view)
    }
}