package com.sevenpeakssoftware.thong.utils

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