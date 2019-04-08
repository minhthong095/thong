package com.sevenpeakssoftware.thong.config.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tblArticle")
data class Article (
    @Expose
    @PrimaryKey
    var id: Long?= null,

    @Expose
    @SerializedName("title")
    @ColumnInfo(name = "Title")
    var title: String?= null,

    @Expose
    @SerializedName("dateTime")
    @ColumnInfo(name = "DateTime")
    var dateTime: String?= null,

    @Expose
    @SerializedName("image")
    var image: ByteArray? = null,

    @Expose
    @SerializedName("ingress")
    @ColumnInfo(name = "Ingress")
    var ingress: String?= null,

    @Expose
    @SerializedName("created")
    @ColumnInfo(name = "Created")
    var created: String?= null,

    @Expose
    @SerializedName("changed")
    @ColumnInfo(name = "Changed")
    var changed: String?= null
)

//{
//    constructor(response: ArticleResponse): this(
//        id = response.id,
//        title = response.title,
//        dateTime = response.dateTime,
//        ingress = response.ingress,
//        image = response.image,
//        created = response.created,
//        changed = response.changed
//    )
//}
