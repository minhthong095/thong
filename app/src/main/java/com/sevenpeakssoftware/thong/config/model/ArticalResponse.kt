package com.sevenpeakssoftware.thong.config.model

import com.google.gson.annotations.SerializedName

data class ArticalResponse(

    @SerializedName("id")
    var id: Long? = null,

    @SerializedName("title")
    var title: String = "",

    @SerializedName("dateTime")
    var dateTime: String? = null,

    @SerializedName("ingress")
    var ingress: String? = null,

    @SerializedName("image")
    var image: String? = null,

    @SerializedName("created")
    var created: String? = null,

    @SerializedName("changed")
    var changed: String? = null
)