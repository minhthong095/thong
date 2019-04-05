package com.sevenpeakssoftware.thong.utils

import java.text.SimpleDateFormat
import java.util.*

fun String.toDateTime(fromFormat: String = "dd.MM.yyyy HH:mm"): Date {
    val parser = SimpleDateFormat(fromFormat, Locale.getDefault())
    parser.timeZone = TimeZone.getTimeZone("UTC")
    return parser.parse(this)
}