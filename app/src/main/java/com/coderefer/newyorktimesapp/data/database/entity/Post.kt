package com.coderefer.newyorktimesapp.data.database.entity

import androidx.room.*
import com.coderefer.newyorktimesapp.util.DateConverter
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "Post")
@TypeConverters(DateConverter::class)
data class Post(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "postId")
    @SerializedName("postId")
    val postId: Long,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String,

    @ColumnInfo(name = "abstract")
    @SerializedName("abstract")
    val desc: String,

    @ColumnInfo(name = "url")
    @SerializedName("url")
    val url: String,

    @ColumnInfo(name = "created_date")
    @SerializedName("created_date")
    val createdDate: Date,

    @Ignore
    @SerializedName("multimedia")
    val multiMedia: List<MultiMedia>
) {
    constructor(
        postId: Long,
        title: String,
        desc: String,
        url: String,
        createdDate: Date
    ): this(postId, title, desc, url, createdDate, emptyList())
}