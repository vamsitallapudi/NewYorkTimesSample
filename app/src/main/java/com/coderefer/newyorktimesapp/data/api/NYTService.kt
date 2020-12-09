package com.coderefer.newyorktimesapp.data.api

import com.coderefer.newyorktimesapp.data.home.HomePosts
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NYTService {

    @GET("home.json")
    suspend fun getPostsAsync(@Query("api-key") key:String): Deferred<Response<HomePosts>>

    companion object {
        const val BASE_URL = "https://api.nytimes.com/svc/topstories/v2/"
    }
    
}