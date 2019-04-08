package com.sevenpeakssoftware.thong.config.model

import com.google.gson.annotations.SerializedName

data class ListArticleResponse(

    @SerializedName("content")
    var content: List<ArticleResponse>? = null

)