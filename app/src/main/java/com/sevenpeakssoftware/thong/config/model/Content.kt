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
            parentColumns = arrayOf("IdArticle"),
            childColumns = arrayOf("IdContent"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    )
)
data class Content(
    @Expose
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IdContent")
    var idContent: Int? = null,

    @Expose
    @SerializedName("idArticle")
    @ColumnInfo(name = "IdArticle")
    var idArticle: Long? = null,

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

{
    constructor(idArticle: Long?, response: ContentResponse): this(
        idArticle = idArticle,
        type = response.type,
        subject = response.subject,
        description = response.description
    )
}
