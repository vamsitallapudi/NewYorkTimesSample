package com.coderefer.newyorktimesapp.data.home

import com.coderefer.newyorktimesapp.data.Result
import com.coderefer.newyorktimesapp.data.database.entity.Post
import com.coderefer.newyorktimesapp.data.home.local.PostLocalDataSource
import com.coderefer.newyorktimesapp.data.home.remote.PostRemoteDataSource
import com.coderefer.newyorktimesapp.data.repo.networkBoundResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow


class PostRepo(
    private val remoteDataSource: PostRemoteDataSource,
    private val localDataSource: PostLocalDataSource
) {

    @ExperimentalCoroutinesApi
    suspend fun fetchPosts(): Flow<Result<*>> {
        return networkBoundResource(
            fetchFromLocal = { localDataSource.postDao.getPosts() },
            shouldFetchFromRemote = { (it as List<Post>).isEmpty() },
            fetchFromRemote = { remoteDataSource.fetchPosts() },
            saveRemoteData = {
                (it as List<Post>).forEach { post ->
                    val postId = localDataSource.postDao.insertPost(post)
                    localDataSource.postDao.insertMultiMedia(postId, post.multiMedia)
                }
            },
        )
    }

//    private suspend fun saveToLocalDB(data: HomePosts) {
//        for (i in data.results) {
//            insert(i)
//        }
//    }

}
