package com.sopian.mygithub.core.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(text: String) {
    Snackbar.make(this, text, Snackbar.LENGTH_SHORT).show()
}