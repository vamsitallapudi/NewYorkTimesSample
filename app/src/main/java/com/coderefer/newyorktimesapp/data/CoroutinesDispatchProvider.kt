package com.coderefer.newyorktimesapp.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import javax.inject.Inject

/**
 * Provides coroutines context.
 */
data class CoroutinesDispatchProvider(
        val main: CoroutineDispatcher,
        val computation: CoroutineDispatcher,
        val io: CoroutineDispatcher
) {

        @Inject
        constructor() : this(Main, Default, IO)
}