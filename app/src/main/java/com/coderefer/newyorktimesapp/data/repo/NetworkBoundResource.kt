package com.coderefer.newyorktimesapp.data.repo

import com.coderefer.newyorktimesapp.data.Result
import com.coderefer.newyorktimesapp.data.home.Post
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
inline fun <T:Any>networkBoundResource(
    crossinline fetchFromLocal: () -> Flow<T>,
    crossinline shouldFetchFromRemote: (T) -> Boolean = { true },
    crossinline fetchFromRemote: suspend () -> Flow<T>,
    crossinline saveRemoteData: suspend (T) -> Unit = { Unit }
) = flow<Result<*>> {

    emit(Result.Loading(null))

    val localData: T = fetchFromLocal().first()

    if (shouldFetchFromRemote(localData)) {

        emit(Result.Loading(localData))

        fetchFromRemote().collect { result ->
            when (result) {
                is Result.Success<*> -> {
                    saveRemoteData(result.data as T)
                    emitAll(fetchFromLocal().map { dbData ->
                        Result.Success(dbData)
                    })
                }

                is Result.Error -> {
                    emitAll(fetchFromLocal().map {
                        Result.Error(
                            Exception("error fetching from remote")
                        )
                    })
                }
                is Result.Loading<*> -> {
                    Result.Loading("Loading...")
                }
            }
        }
    } else {
        emitAll(fetchFromLocal().map { Result.Success(it) })
    }
}