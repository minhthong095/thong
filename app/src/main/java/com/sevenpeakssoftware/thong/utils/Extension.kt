package com.sevenpeakssoftware.thong.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.view.WindowManager
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.*

fun String.toOtherTimeFormat(
    fromFormat: String = "dd.MM.yyyy HH:mm",
    toFormat: String = "dd MMMM yyyy HH:mm",
    fromTimeZone: String = "UTC",
    toTimeZone: String = TimeZone.getDefault().id
): String {
    val sdf = SimpleDateFormat(fromFormat)
    sdf.timeZone = TimeZone.getTimeZone(fromTimeZone)
    val d = sdf.parse(this)
    val sdfTo = SimpleDateFormat(toFormat)
    sdfTo.timeZone = TimeZone.getTimeZone(toTimeZone)
    return sdfTo.format(d)
}

fun Context.getSize(): Point {
    val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val size = Point()
    windowManager.defaultDisplay.getSize(size)
    return size
}