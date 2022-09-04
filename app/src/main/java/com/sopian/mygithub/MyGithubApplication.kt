package com.sopian.mygithub

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyGithubApplication : Application() {

    override fun onLowMemory() {
        super.onLowMemory()
        GlideApp.get(this).clearMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        GlideApp.get(this).trimMemory(level)
    }
}