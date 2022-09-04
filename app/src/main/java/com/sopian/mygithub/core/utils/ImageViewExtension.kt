package com.sopian.mygithub.core.utils

import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.sopian.mygithub.GlideApp

const val CROSS_FADE_DURATION = 350

fun ImageView.loadPhotoUrl(
    url: String
) {
    GlideApp.with(context)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade(CROSS_FADE_DURATION))
        .into(this)
        .clearOnDetach()
}