package com.coderefer.newyorktimesapp.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PostAndMultiMedia(
    @Embedded
    val post: Post,
    @Relation(
        parentColumn = "postId",
        entity = MultiMedia::class,
        entityColumn = "multimediaPostId"
    )
    val multiMedia: List<MultiMedia>
)