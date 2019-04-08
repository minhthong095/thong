package com.sevenpeakssoftware.thong.view.main.item

import androidx.databinding.ObservableField
import com.sevenpeakssoftware.thong.config.model.Article
import com.sevenpeakssoftware.thong.config.model.ArticleResponse
import com.sevenpeakssoftware.thong.utils.toOtherTimeFormat
import com.sevenpeakssoftware.thong.view.base.BaseViewModel
import java.util.*

class ArticleCellViewModel : BaseViewModel {

    val bindTitle = ObservableField<String>()
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

    constructor(articleResponse: ArticleResponse) :
            this( articleResponse.title, articleResponse.image, articleResponse.dateTime, articleResponse.ingress)

    constructor(article: Article) :
            this( article.title, article.image, article.dateTime, article.ingress)

    private fun _getCastDate(raw: String?): String {
        if (raw == null) return ""

        val yearNow = Calendar
            .getInstance(TimeZone.getTimeZone(TimeZone.getDefault().id))
            .get(Calendar.YEAR)

        val yearArticle = raw
            .toOtherTimeFormat(toFormat = "yyyy")
            .toInt()

        lateinit var showFormat: String

        if (yearNow != yearArticle) showFormat = "dd MMMM yyyy"
        else showFormat = "dd MMMM"

        return raw.toOtherTimeFormat(toFormat = showFormat)
    }
}