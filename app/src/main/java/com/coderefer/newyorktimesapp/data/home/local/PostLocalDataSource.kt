package com.coderefer.newyorktimesapp.data.home.local

import androidx.annotation.WorkerThread
import com.coderefer.newyorktimesapp.data.database.PostDao
import com.coderefer.newyorktimesapp.data.home.Post
import kotlinx.coroutines.flow.Flow

class PostLocalDataSource(private val postDao: PostDao) {
    val allPosts: Flow<List<Post>> = postDao.getPosts()


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(post: Post) {
        postDao.insert(post)
    }
}