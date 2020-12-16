package com.coderefer.newyorktimesapp.data.home

import androidx.annotation.WorkerThread
import com.coderefer.newyorktimesapp.data.Result
import com.coderefer.newyorktimesapp.data.home.local.PostLocalDataSource
import com.coderefer.newyorktimesapp.data.home.remote.PostRemoteDataSource
import com.coderefer.newyorktimesapp.data.repo.networkBoundResource
import com.coderefer.newyorktimesapp.util.RateLimiter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.TimeUnit

class PostRepo(
    private val remoteDataSource: PostRemoteDataSource,
    private val localDataSource: PostLocalDataSource
) {

    @ExperimentalCoroutinesApi
    suspend fun fetchPosts(): Flow<Result<*>> {
        return networkBoundResource(
            fetchFromLocal = { localDataSource.postDao.getPosts() },
            shouldFetchFromRemote = { (it as List<Post>).size < 1 },
            fetchFromRemote = { remoteDataSource.fetchPosts() },
            saveRemoteData = { localDataSource.postDao.insertAll(it as List<Post>) },
        )
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
