package com.sevenpeakssoftware.thong.config.model

import com.google.gson.annotations.SerializedName

data class ArticleResponse(

    @SerializedName("id")
    var id: Long? = null,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("dateTime")
    var dateTime: String? = null,

    @SerializedName("tags")
    var tags: Array<String>? = null,

    @SerializedName("content")
    var content: Array<ContentResponse>? = null,

    @SerializedName("ingress")
    var ingress: String? = null,

    @SerializedName("image")
    var image: String? = null,

    @SerializedName("created")
    var created: String? = null,

    @SerializedName("changed")
    var changed: String? = null
)

data class ContentResponse (

    @SerializedName("type")
    var type: String? = null,

    @SerializedName("subject")
    var subject: String? = null,

    @SerializedName("description")
    var description: String? = null
)