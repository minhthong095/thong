package com.sevenpeakssoftware.thong.config.model

import com.google.gson.annotations.SerializedName

data class ListArticalResponse(

    @SerializedName("content")
    var content: List<ArticalResponse>? = null

)