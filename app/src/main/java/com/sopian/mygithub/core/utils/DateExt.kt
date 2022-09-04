package com.sopian.mygithub.core.utils

import android.annotation.SuppressLint
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun String.formatTimeAgo(): String? {
    try{
        val format = "yyyy-MM-dd'T'HH:mm:ss'Z'"

        val sdf = SimpleDateFormat(format, Locale.getDefault())

        val past = sdf.parse(this)

        var result = format.format(past)

        val parsedMonth = sdf.parse(this)
        val monthFormatted = SimpleDateFormat("dd MMM")
        val createdMonth = parsedMonth?.let { monthFormatted.format(it) }

        val parsedYear = sdf.parse(this)
        val yearFormatted = SimpleDateFormat("dd MMM yyyy")
        val createdYear = parsedYear?.let { yearFormatted.format(it) }

        val diff = Calendar.getInstance().time.time - (past?.time ?: 0)

        val oneSec = 1000L
        val oneMin: Long = 60 * oneSec
        val oneHour: Long = 60 * oneMin
        val oneDay: Long = 24 * oneHour
        val oneMonth: Long = 30 * oneDay
        val oneYear: Long = 365 * oneDay

        val diffMin: Long = diff / oneMin
        val diffHours: Long = diff / oneHour
        val diffDays: Long = diff / oneDay
        val diffMonths: Long = diff / oneMonth
        val diffYears: Long = diff / oneYear

        when {
            diffYears > 0 -> {
                if (createdYear != null) {
                    result = "on $createdYear"
                }
            }
            diffMonths > 0 && diffYears < 1 -> {
                if (createdMonth != null) {
                    result = "on $createdMonth"
                }
            }
            diffDays > 0 && diffMonths < 1 -> {
                result = "${(diffDays - diffMonths / 30)} days ago"
            }
            diffHours > 0 && diffDays < 1 -> {
                result = "${(diffHours - diffDays * 24)} hours ago"
            }
            diffMin > 0 && diffHours < 1 -> {
                result = "${(diffMin - diffHours * 60)} min ago"
            }
            diffMin < 1 -> {
                result = "just now"
            }
        }
        return result
    }catch (ex:java.lang.Exception){
        Log.e("formatTimeAgo",ex.toString())
        return null
    }
}