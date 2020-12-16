package com.coderefer.newyorktimesapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coderefer.newyorktimesapp.data.home.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query("SELECT * FROM post_table ORDER BY created_date asc")
    fun getPosts(): Flow<List<Post>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(post: Post)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(posts: List<Post>)
}