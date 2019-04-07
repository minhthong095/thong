package com.sevenpeakssoftware.thong.view.main.item

import android.app.Application
import androidx.databinding.ObservableField
import com.sevenpeakssoftware.thong.config.model.ArticalResponse
import com.sevenpeakssoftware.thong.utils.toOtherTimeFormat
import com.sevenpeakssoftware.thong.view.base.BaseViewModel
import java.text.DateFormat
import java.time.LocalDateTime
import java.util.*

class ArticalCellViewModel : BaseViewModel {

    val bindTitle = ObservableField<String>()
    val bindImage = ObservableField<String>()
    val bindTime = ObservableField<String>()
    val bindIngress = ObservableField<String>()

    constructor()

    constructor(articalResponse: ArticalResponse) {
        bindTitle.set(articalResponse.title ?: "")
        bindImage.set(articalResponse.image ?: "")
        bindTime.set(
            if (articalResponse.dateTime == null) ""
            else {
                val dateNow = Calendar.getInstance(TimeZone.getTimeZone(TimeZone.getDefault().id))
                val yearNow = dateNow.get(Calendar.YEAR)
                val yearArtical = articalResponse.dateTime!!.toOtherTimeFormat(toFormat = "yyyy").toInt()

                lateinit var showFormat: String

                if (yearNow != yearArtical) showFormat = "dd MMMM yyyy "
                else showFormat = "dd MMMM "
//                android.text.format.DateFormat.is24HourFormat(MainA)

                "a"
            }
        )
        bindIngress.set(articalResponse.ingress ?: "")
    }
}