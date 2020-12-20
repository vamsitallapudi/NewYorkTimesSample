package com.coderefer.newyorktimesapp.data.database.dao

import androidx.room.*
import com.coderefer.newyorktimesapp.data.database.entity.MultiMedia
import com.coderefer.newyorktimesapp.data.database.entity.Post
import com.coderefer.newyorktimesapp.data.database.entity.PostAndMultiMedia
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Transaction
    @Query("SELECT * FROM Post ORDER BY created_date asc")
    fun getPosts(): Flow<List<PostAndMultiMedia>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: Post): Long //return type is key here

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<Post>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMediaList(multiMedia: List<MultiMedia>)

    suspend fun insertMultiMedia(postId:Long, multiMedia: List<MultiMedia>) {
        multiMedia.forEach {
            it.multimediaPostId=postId }
        insertMediaList(multiMedia)
    }
}