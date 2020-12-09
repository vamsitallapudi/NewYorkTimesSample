package com.coderefer.newyorktimesapp

import android.app.Application
import com.coderefer.newyorktimesapp.data.database.PostsRoomDatabase
import com.coderefer.newyorktimesapp.data.home.PostRepo
import com.coderefer.newyorktimesapp.data.home.local.PostLocalDataSource
import com.coderefer.newyorktimesapp.data.home.remote.PostRemoteDataSource

class NYTApp : Application() {

    val database by lazy { PostsRoomDatabase.getDatabase(this) }
    val localDataSource by lazy { PostLocalDataSource(database.postDao()) }
    val repository by lazy { PostRepo(PostRemoteDataSource(null), localDataSource) }


    override fun onCreate() {
        super.onCreate()

    }
}