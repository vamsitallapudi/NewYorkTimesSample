package com.coderefer.newyorktimesapp.data.home.remote

import android.content.Context
import android.util.Log
import com.coderefer.newyorktimesapp.BuildConfig
import com.coderefer.newyorktimesapp.data.api.NYTService
import com.coderefer.newyorktimesapp.util.safeApiCall
import com.coderefer.newyorktimesapp.data.Result
import com.coderefer.newyorktimesapp.data.home.HomePosts
import com.coderefer.newyorktimesapp.data.home.Post
import com.coderefer.newyorktimesapp.util.NYT_KEY
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

//TODO add service as DI
class PostRemoteDataSource(private val context: Context) {

    private val refreshIntervalMs: Long = 1000 * 60

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        val debugLevel = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return HttpLoggingInterceptor().apply {
            level = debugLevel
        }
    }

    private fun getCachefile(context: Context): File {
        val cacheFile = File(context.cacheDir, "okhttp_cache")
        cacheFile.mkdirs()
        return cacheFile
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(15L, TimeUnit.SECONDS)
            .readTimeout(15L, TimeUnit.SECONDS)
            .writeTimeout(15L, TimeUnit.SECONDS)
//            .addInterceptor(clientAuthInterceptor) not required
            .addInterceptor(loggingInterceptor())
            .cache(Cache(getCachefile(context), 10 * 1024 * 1024 /* 10 MB cache*/))
            .build()
    }
    private val service by lazy {
        Retrofit.Builder()
            .baseUrl(NYTService.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(NYTService::class.java)
    }

    suspend fun fetchPosts() : Flow<Result<List<Post>>> {
        return safeApiCall(
            call = { requestPosts },
            errorMessage = "Error fetching posts"
        )
    }

    private val requestPosts: Flow<Result<List<Post>>> = flow {
        while (true) {
            try {
                val response = service!!.getPostsAsync(NYT_KEY).await()
                if (response.isSuccessful) {
                    val postList = response.body()?.results
                    if (postList != null) {
                        emit(Result.Success(postList))
                        delay(refreshIntervalMs)
                    } else {
                        emit(Result.Error(IOException("Story List Retrieval failed")))
                        delay(refreshIntervalMs)
                    }
                }
            } catch (e: Exception) {
                Log.d(PostRemoteDataSource::class.simpleName, e.toString())
                emit( Result.Error(Exception("Error calling request Posts API")))
                delay(refreshIntervalMs)
            }
        }
    }
}