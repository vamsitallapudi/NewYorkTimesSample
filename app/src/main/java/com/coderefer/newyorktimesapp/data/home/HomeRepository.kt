package com.coderefer.newyorktimesapp.data.home

import com.coderefer.newyorktimesapp.data.home.local.HomeLocalDataSource
import com.coderefer.newyorktimesapp.data.home.remote.HomeRemoteDataSource

class HomeRepository(val remoteDataSource: HomeRemoteDataSource, val localDataSource: HomeLocalDataSource){

}
