package com.coderefer.newyorktimesapp.data.home

import androidx.annotation.WorkerThread
import com.coderefer.newyorktimesapp.data.Result
import com.coderefer.newyorktimesapp.data.home.local.PostLocalDataSource
import com.coderefer.newyorktimesapp.data.home.remote.PostRemoteDataSource
import kotlinx.coroutines.flow.Flow

class PostRepo(
    private val remoteDataSource: PostRemoteDataSource,
    private val localDataSource: PostLocalDataSource
) {
    val allPosts : Flow<List<Post>> = localDataSource.allPosts

    suspend fun fetchPostsFromNetwork():  Result<HomePosts> {
        val result = remoteDataSource.fetchPosts()
        if (result is Result.Success) {
            saveToLocalDB(result.data)
        }
        return result
    }

    private suspend fun saveToLocalDB(data: HomePosts) {
        for (i in data.results) {
            insert(i)
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(post: Post) {
        localDataSource.insert(post)
    }
}
