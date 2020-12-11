package com.coderefer.newyorktimesapp.data.home

import androidx.annotation.WorkerThread
import com.coderefer.newyorktimesapp.data.Result
import com.coderefer.newyorktimesapp.data.home.local.PostLocalDataSource
import com.coderefer.newyorktimesapp.data.home.remote.PostRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class PostRepo(
    private val remoteDataSource: PostRemoteDataSource,
    private val localDataSource: PostLocalDataSource
) {
//    val getPosts : Flow<List<Post>> = localDataSource.getPosts
//    suspend fun fetchPosts(): Result<List<Post>> {
//        val posts = localDataSource.getPosts
//        if (posts ==null){
//
//        }
//    }

    suspend fun fetchPostsFromNetwork():  Flow<Result<HomePosts>> {
        return remoteDataSource.fetchPosts()
            .onEach {
                if (it is Result.Success) {
                    saveToLocalDB(it.data)
                }
            }
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
