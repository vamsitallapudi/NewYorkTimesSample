package com.coderefer.newyorktimesapp.ui.home

import androidx.lifecycle.*
import com.coderefer.newyorktimesapp.data.CoroutinesDispatchProvider
import com.coderefer.newyorktimesapp.data.database.entity.Post
import com.coderefer.newyorktimesapp.data.home.PostRepo
import com.coderefer.newyorktimesapp.util.event.Event
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.coderefer.newyorktimesapp.data.Result
import com.coderefer.newyorktimesapp.data.database.entity.PostAndMultiMedia
import kotlinx.coroutines.flow.collect

class HomeViewModel @Inject constructor(private val postRepo: PostRepo) : ViewModel() {

    private val _uiState = MutableLiveData<HomeUIModel>()
    val uiState: LiveData<HomeUIModel>
        get() = _uiState
    private val postsMutableLiveData = MutableLiveData<Result<List<PostAndMultiMedia>>>()
    val postsLiveData: LiveData<Result<List<PostAndMultiMedia>>>
        get() = postsMutableLiveData

    //    TODO: Replace with DI
    private val dispatchProvider by lazy { CoroutinesDispatchProvider() }

    fun fetchPosts(): Job {
        return viewModelScope.launch(dispatchProvider.computation) {
            withContext(dispatchProvider.main) {
                showLoading()
            }
            val result = postRepo.fetchPosts()

            result.collect {
                when (it) {
                    is Result.Success<*> -> {
                        postsMutableLiveData.postValue(Result.Success(it.data as List<PostAndMultiMedia>))
                    }
                    is Result.Error -> postsMutableLiveData.postValue(it)
                    is Result.Loading -> {
                        //TODO()
                    }
                }
            }
        }
    }

    private fun showLoading() {
        emitUIState(showProgress = true)
    }

    private fun emitUIState(
        showProgress: Boolean = false,
        showError: Event<Int>? = null,
        showSuccess: Event<HomeUIModel>? = null
    ) {
        val uiModel = HomeUIModel(showProgress, showError, showSuccess)
        _uiState.value = uiModel
    }

    /**
     * UI model for [HomeActivity]
     */
    data class HomeUIModel(
        val showProgress: Boolean,
        val showError: Event<Int>?,
        val showSuccess: Event<HomeUIModel>?
    )

}