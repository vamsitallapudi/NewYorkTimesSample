package com.coderefer.newyorktimesapp.data.home

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.coderefer.newyorktimesapp.util.DateConverter
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "post_table")
@TypeConverters(DateConverter::class)
data class Post(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Long,

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
    val createdDate: Date
)