package com.sevenpeakssoftware.thong.config.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tblArticle")
data class Article (
    @PrimaryKey
    @ColumnInfo(name = "IdArticle")
    var idArticle: Long?= null,

    @ColumnInfo(name = "Title")
    var title: String?= null,

    @ColumnInfo(name = "DateTime")
    var dateTime: String?= null,

    @ColumnInfo(name = "Image")
    var image: ByteArray? = null,

    @ColumnInfo(name = "Ingress")
    var ingress: String?= null,

    @ColumnInfo(name = "Created")
    var created: String?= null,

    @ColumnInfo(name = "Changed")
    var changed: String?= null
)
