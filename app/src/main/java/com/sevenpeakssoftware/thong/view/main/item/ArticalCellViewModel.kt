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
    val bindByteImage = ObservableField<ByteArray?>()
    val bindDate = ObservableField<String>()
    val bindIngress = ObservableField<String>()
    val bindHour = ObservableField<String?>()

    constructor()

    constructor(articalResponse: ArticalResponse) {
        bindTitle.set(articalResponse.title ?: "")
        bindUrlImage.set(articalResponse.image)
        bindDate.set(_getCastDate(articalResponse.dateTime))
        bindHour.set(articalResponse.dateTime)
        bindIngress.set(articalResponse.ingress ?: "")
    }

    constructor(articalOffline: Artical) {
        bindTitle.set(articalOffline.title ?: "")
        bindByteImage.set(articalOffline.image)
        bindDate.set(_getCastDate(articalOffline.dateTime))
        bindHour.set(articalOffline.dateTime)
        bindIngress.set(articalOffline.ingress ?: "")
    }

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