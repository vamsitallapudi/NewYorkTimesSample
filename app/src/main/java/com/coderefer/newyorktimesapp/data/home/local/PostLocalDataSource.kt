package com.coderefer.newyorktimesapp.data.home.local

import androidx.annotation.WorkerThread
import com.coderefer.newyorktimesapp.data.database.PostDao
import com.coderefer.newyorktimesapp.data.home.Post
import kotlinx.coroutines.flow.Flow
import com.coderefer.newyorktimesapp.data.Result

class PostLocalDataSource(val postDao: PostDao) {


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(post: Post) {
        postDao.insert(post)
    }
}