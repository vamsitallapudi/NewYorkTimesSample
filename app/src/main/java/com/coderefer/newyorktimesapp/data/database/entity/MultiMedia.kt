package com.coderefer.newyorktimesapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "multimedia")
data class MultiMedia(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "multimediaId")
    @SerializedName("multimediaId")
    val multimediaId: Long,

    var multimediaPostId:Long,//foreign key

    @ColumnInfo(name = "url")
    @SerializedName("url")
    val url: String,

    @ColumnInfo(name = "format")
    @SerializedName("format")
    val format: String
)
