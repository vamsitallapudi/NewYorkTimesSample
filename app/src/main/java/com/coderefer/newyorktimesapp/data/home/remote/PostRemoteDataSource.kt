package com.coderefer.newyorktimesapp.data.home.remote

import android.util.Log
import com.coderefer.newyorktimesapp.data.api.NYTService
import com.coderefer.newyorktimesapp.util.safeApiCall
import com.coderefer.newyorktimesapp.data.Result
import com.coderefer.newyorktimesapp.data.home.HomePosts
import com.coderefer.newyorktimesapp.util.NYT_KEY
import java.io.IOException

class PostRemoteDataSource(private val nytService : NYTService?) {

    suspend fun fetchPosts() = safeApiCall(
        call = {requestPosts()},
        errorMessage = "Error fetching posts"
    )
    private suspend fun requestPosts() : Result<HomePosts> {

        try {
            val response = nytService!!.getPostsAsync(NYT_KEY).await()
            if (response.isSuccessful) {
                val postList = response.body()
                if (postList != null) {
                    return Result.Success(postList)
                }
                return Result.Error(IOException("Story List Retrieval failed"))
            }
        } catch (e: Exception) {
            Log.d(PostRemoteDataSource::class.simpleName, e.toString())
        }
        return Result.Error(Exception("Error calling request Posts API"))
    }
}