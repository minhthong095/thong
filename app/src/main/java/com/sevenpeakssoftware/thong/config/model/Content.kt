package com.sevenpeakssoftware.thong.config.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tblContent",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Article::class,
            parentColumns = arrayOf("IdArticle"),
            childColumns = arrayOf("IdArticle"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    )
)
data class Content(
    
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IdContent")
    var idContent: Long? = null,

    @ColumnInfo(name = "IdArticle")
    var idArticle: Long? = null,

    @ColumnInfo(name = "Type")
    var type: String? = null,

    
    @ColumnInfo(name = "Subject")
    var subject: String? = null,

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
