package com.coderefer.newyorktimesapp.data.home

import androidx.annotation.WorkerThread
import com.coderefer.newyorktimesapp.data.home.local.PostLocalDataSource
import com.coderefer.newyorktimesapp.data.home.remote.PostRemoteDataSource
import kotlinx.coroutines.flow.Flow

class PostRepo(
    private val remoteDataSource: PostRemoteDataSource,
    private val localDataSource: PostLocalDataSource
) {
    val allPosts : Flow<List<Post>> = localDataSource.allPosts

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(post: Post) {
        localDataSource.insert(post)
    }
}
