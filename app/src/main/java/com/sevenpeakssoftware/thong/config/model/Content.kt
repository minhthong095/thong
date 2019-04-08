package com.sevenpeakssoftware.thong.config.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "tblContent",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Article::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("articleId"),
            onDelete = ForeignKey.CASCADE
        )
    )
)
data class Content(
    @Expose
    @PrimaryKey(autoGenerate = true)
    var contentid: Int? = null,

    @Expose
    @SerializedName("articleId")
    @ColumnInfo(name = "ArticleId")
    var articleId: Long? = null,

    @Expose
    @SerializedName("type")
    @ColumnInfo(name = "Type")
    var type: String? = null,

    @Expose
    @SerializedName("subject")
    @ColumnInfo(name = "Subject")
    var subject: String? = null,

    @Expose
    @SerializedName("description")
    @ColumnInfo(name = "Description")
    var description: String? = null
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
