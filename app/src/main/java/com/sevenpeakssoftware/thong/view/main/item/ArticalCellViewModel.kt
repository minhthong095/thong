package com.sevenpeakssoftware.thong.view.main.item

import androidx.databinding.ObservableField
import com.sevenpeakssoftware.thong.config.model.Artical
import com.sevenpeakssoftware.thong.config.model.ArticalResponse
import com.sevenpeakssoftware.thong.utils.toOtherTimeFormat
import com.sevenpeakssoftware.thong.view.base.BaseViewModel
import java.util.*

class ArticalCellViewModel : BaseViewModel {

    val bindTitle = ObservableField<String>()
    val bindUrlImage = ObservableField<String?>()
    val bindDate = ObservableField<String>()
    val bindIngress = ObservableField<String>()
    val bindHour = ObservableField<String?>()
    val bindImage = ObservableField<Any?>()

    constructor(title: String?, image: Any?, dateTime: String?, ingress: String?) {
        bindTitle.set(title ?: "")
        bindImage.set(image)
        bindDate.set(_getCastDate(dateTime))
        bindHour.set(dateTime)
        bindIngress.set(ingress ?: "")
    }

    constructor(articalResponse: ArticalResponse) :
            this( articalResponse.title, articalResponse.image, articalResponse.dateTime, articalResponse.ingress)

    constructor(artical: Artical) :
            this( artical.title, artical.image, artical.dateTime, artical.ingress)

    private fun _getCastDate(raw: String?): String {
        if (raw == null) return ""

        val yearNow = Calendar
            .getInstance(TimeZone.getTimeZone(TimeZone.getDefault().id))
            .get(Calendar.YEAR)

        val yearArtical = raw
            .toOtherTimeFormat(toFormat = "yyyy")
            .toInt()

        lateinit var showFormat: String

        if (yearNow != yearArtical) showFormat = "dd MMMM yyyy"
        else showFormat = "dd MMMM"

        return raw.toOtherTimeFormat(toFormat = showFormat)
    }
}